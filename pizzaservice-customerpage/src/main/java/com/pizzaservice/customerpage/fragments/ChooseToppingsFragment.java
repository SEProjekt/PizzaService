package com.pizzaservice.customerpage.fragments;

import com.pizzaservice.api.buissness_objects.PizzaConfiguration;
import com.pizzaservice.api.buissness_objects.PizzaSize;
import com.pizzaservice.api.buissness_objects.Topping;
import com.pizzaservice.customerpage.MyUtils;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.data_access_objects.ToppingDAO;
import com.pizzaservice.common.Utils;
import com.pizzaservice.common.items.ToppingItem;
import com.pizzaservice.customerpage.views.ToppingSelectorView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 18.01.17.
 */
public class ChooseToppingsFragment extends Fragment
{
    @FXML VBox vbContainer;

    @FXML public void actionNext( ActionEvent actionEvent ) { next(); }
    @FXML public void actionAbort( ActionEvent actionEvent ) { abort(); }

    /**
     * Button to add a topping selector. This button will be displayed under the list of topping selectors
     * and not be displayed when the maximum number of toppings is reached.
     */
    private Button btnAddToppingSelector;

    /**
     * The topping selectors to which each store a list of topping items.
     * Since it should not be possible to select the same topping twice, once a topping is selected by a
     * certain topping selector, all other topping selectors should remove the selected topping item from their list.
     */
    private List<ToppingSelectorView> toppingSelectorViews;

    /**
     * The maximum number of toppings (and therefor topping selectors) to choose.
     * This number depends on the pizza size (which must be previously entered in the current pizza configuration).
     */
    private int maxToppingCount;

    /**
     * A list of topping items available which serves as the input of newly created topping selectors
     * and must also be updated whenever a topping is selected.
     */
    private List<ToppingItem> toppingItems;

    public ChooseToppingsFragment( Fragment oldFragment )
    {
        super( "choose_toppings.fxml", oldFragment );
    }

    @Override
    public void setup()
    {
        try
        {
            btnAddToppingSelector = new Button( "Topping hinzufügen" );
            toppingSelectorViews = new ArrayList<>();

            setupMaxToppingCount();
            setupToppingItems();

            btnAddToppingSelector.setOnAction( event -> addToppingSelector() );

            vbContainer.getChildren().add( btnAddToppingSelector );
        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e, this );
        }
    }

    public ChooseToppingsFragment addToppingSelector( int toppingIndex )
    {
        ToppingSelectorView selector = addToppingSelector();
        if( selector == null )
            return this;

        selector.select( toppingIndex );

        return this;
    }

    public ChooseToppingsFragment deleteToppingSelector( int selectorIndex )
    {
        deleteToppingSelector( toppingSelectorViews.get( selectorIndex ) );
        return this;
    }

    public Fragment next()
    {
        PizzaConfiguration currentConfiguration = session.getCurrentPizzaConfiguration();

        Collection<Topping> toppings = new ArrayList<>();

        for( ToppingSelectorView toppingSelectorView : toppingSelectorViews )
        {
            ToppingItem selectedToppingItem = toppingSelectorView.getSelectedToppingItem();
            if( selectedToppingItem == null )
            {
                Utils.showInputErrorMessage( "Bitte wähle deine Toppings aus!" );
                return this;
            }

            toppings.add( selectedToppingItem.getTopping() );
        }

        currentConfiguration.setToppings( toppings );

        return setNewFragment( new FinishPizzaConfigurationFragment( this ) );
    }

    public Fragment abort()
    {
        return setNewFragment( new MainMenuFragment( this ) );
    }

    private void setupToppingItems() throws DataAccessException
    {
        toppingItems = new ArrayList<>();

        ToppingDAO toppingDAO = daoBundle.getToppingDAO();
        Collection<Topping> toppings = toppingDAO.getToppings();
        for( Topping topping : toppings )
        {
            ToppingItem toppingItem = new ToppingItem( topping );
            toppingItems.add( toppingItem );
        }
    }

    private void setupMaxToppingCount()
    {
        PizzaConfiguration currentConfiguration = session.getCurrentPizzaConfiguration();
        if( currentConfiguration.getPizzaSize() == PizzaSize.SMALL )
            maxToppingCount = 2;
        else if( currentConfiguration.getPizzaSize() == PizzaSize.LARGE )
            maxToppingCount = 3;
        else
            maxToppingCount = 5;
    }

    private ToppingSelectorView addToppingSelector()
    {
        if( toppingSelectorViews.size() >= maxToppingCount )
            return null;

        ToppingSelectorView nextToppingSelectorView = new ToppingSelectorView( toppingItems );

        nextToppingSelectorView.setOnToppingItemChangedListener( this::updateToppingSelectors );
        nextToppingSelectorView.setOnDeleteListener( this::deleteToppingSelector );

        toppingSelectorViews.add( nextToppingSelectorView );
        vbContainer.getChildren().add( vbContainer.getChildren().size() - 1, nextToppingSelectorView );

        // stop adding toppings by removing the "add topping" button when reaching the max number of toppings
        int selectorCount = toppingSelectorViews.size();
        if( selectorCount == maxToppingCount )
            vbContainer.getChildren().remove( btnAddToppingSelector );

        return nextToppingSelectorView;
    }

    /**
     * Called whenever a topping item is selected by some topping selector.
     * It prevents the selection of the same topping twice by removing the newly selected topping item
     * from all other item lists of the available topping selectors (so it cannot be selected by
     * any different topping selector).
     * This of course also means that it must add the previously selected topping item to the other item lists too.
     * @param oldItem
     * @param newItem
     * @param changeSelector the instance of the topping selector which made the new selection
     */
    private void updateToppingSelectors( ToppingItem oldItem, ToppingItem newItem, ToppingSelectorView changeSelector )
    {
        for( ToppingSelectorView selector : toppingSelectorViews )
        {
            if( selector == changeSelector )
                continue;

            List<ToppingItem> toppingItemsOfSelector = selector.getToppingItems();

            if( oldItem != null ) toppingItemsOfSelector.add( oldItem );
            if( newItem != null ) toppingItemsOfSelector.remove( newItem );
        }

        if( oldItem != null ) toppingItems.add( oldItem );
        if( newItem != null ) toppingItems.remove( newItem );
    }

    private void deleteToppingSelector( ToppingSelectorView selector )
    {
        // selected item becomes available now -> update
        ToppingItem selectedItem = selector.getSelectedToppingItem();
        if( selectedItem != null )
            updateToppingSelectors( selectedItem, null, selector );

        // if we reached the max number of toppings we have to add the "add topping" button back again
        if( toppingSelectorViews.size() == maxToppingCount )
            vbContainer.getChildren().add( btnAddToppingSelector );

        toppingSelectorViews.remove( selector );
        vbContainer.getChildren().remove( selector );
    }
}
