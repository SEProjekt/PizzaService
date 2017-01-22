package com.pizzaservice.orderpage.controller;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.buissness_objects.PizzaSize;
import com.pizzaservice.buissness_objects.Topping;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.ToppingDAO;
import com.pizzaservice.data_access_objects_impl.ToppingDatabaseDAO;
import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.fragment.FragmentURLs;
import com.pizzaservice.orderpage.items.ToppingItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 18.01.17.
 */
public class ChooseToppingsController extends Controller
{
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
    private List<ToppingSelector> toppingSelectors;

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

    @FXML
    VBox vbContainer;

    /**
     * UI element to select a topping which contains a choice box of topping items and a delete button
     * to discard the topping selection.
     */
    private class ToppingSelector extends HBox
    {
        private ChoiceBox cbTopping;
        private Button btnDelete;

        public ToppingSelector()
        {
            super();

            setupToppingChoiceBox();
            setupDeleteButton();

            setAlignment( Pos.CENTER );
            setSpacing( 10 );
            getChildren().add( cbTopping );
            getChildren().add( btnDelete );
        }

        private void setupToppingChoiceBox()
        {
            cbTopping = new ChoiceBox();
            cbTopping.setItems( FXCollections.observableArrayList( toppingItems ) );
            cbTopping.getSelectionModel().selectedIndexProperty().addListener( ( ov, oldIndex, newIndex ) ->
            {
                // Very important check: when we remove an item from the item list, we might have
                // selected the item with the biggest index before. Therefore, this index will be updated
                // and that means the oldIndex becomes out of bounds!
                if( oldIndex.intValue() >= getToppingItems().size() )
                    return;

                // must check if unselected
                ToppingItem oldItem = null;
                if( oldIndex.intValue() >= 0 )
                    oldItem = getToppingItems().get( oldIndex.intValue() );

                ToppingItem newItem = getToppingItems().get( newIndex.intValue() );

                updateToppingSelectors( oldItem, newItem, ToppingSelector.this );
            } );
        }

        private void setupDeleteButton()
        {
            btnDelete = new Button( "Entfernen" );
            btnDelete.setOnAction( event ->
            {
                // update topping items
                ToppingItem selectedItem = getSelectedToppingItem();
                if( selectedItem != null )
                    updateToppingSelectors( selectedItem, null, ToppingSelector.this );

                // if we reached the max number of toppings we have to add the "add topping" button back again
                if( toppingSelectors.size() == maxToppingCount )
                    vbContainer.getChildren().add( btnAddToppingSelector );

                toppingSelectors.remove( ToppingSelector.this );
                vbContainer.getChildren().remove( ToppingSelector.this );
            } );
        }

        public ToppingItem getSelectedToppingItem()
        {
            return (ToppingItem) cbTopping.getSelectionModel().getSelectedItem();
        }

        public List<ToppingItem> getToppingItems()
        {
            return cbTopping.getItems();
        }
    }

    @Override
    protected void onSetup()
    {
        try
        {
            btnAddToppingSelector = new Button( "Topping hinzufügen" );
            toppingSelectors = new ArrayList<>();
            setupMaxToppingCount();
            setupToppingItems();

            btnAddToppingSelector.setOnAction( event ->
            {
                vbContainer.getChildren().clear();

                for( ToppingSelector toppingSelector : toppingSelectors )
                    vbContainer.getChildren().add( toppingSelector );

                ToppingSelector nextToppingSelector = new ToppingSelector();
                toppingSelectors.add( nextToppingSelector );
                vbContainer.getChildren().add( nextToppingSelector );

                // stop adding toppings by removing the "add topping" button when reaching the max number of toppings
                if( toppingSelectors.size() < maxToppingCount )
                    vbContainer.getChildren().add( btnAddToppingSelector );
            } );

            vbContainer.getChildren().add( btnAddToppingSelector );
        }
        catch( DataAccessException e )
        {
            Utils.showErrorMessage( e.getMessage() );
        }
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        this.<MainMenuController>setFragment( FragmentURLs.MAIN_MENU );
    }

    @FXML
    public void actionNext( ActionEvent actionEvent ) throws IOException
    {
        PizzaConfiguration currentConfiguration = session.getCurrentPizzaConfiguration();

        Collection<Topping> toppings = new ArrayList<>();

        for( ToppingSelector toppingSelector : toppingSelectors )
        {
            ToppingItem selectedToppingItem = toppingSelector.getSelectedToppingItem();
            if( selectedToppingItem == null )
            {
                Utils.showErrorMessage( "Bitte wähle deine Toppings aus!" );
                return;
            }

            toppings.add( selectedToppingItem.getTopping() );
        }

        currentConfiguration.setToppings( toppings );

        this.<FinishPizzaConfigurationController>setFragment( FragmentURLs.FINISH_PIZZA_CONFIGURATION );
    }

    private void setupToppingItems() throws DataAccessException
    {
        toppingItems = new ArrayList<>();

        ToppingDAO toppingDAO = new ToppingDatabaseDAO( database );
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
        if( currentConfiguration.getSize() == PizzaSize.SMALL )
            maxToppingCount = 2;
        else if( currentConfiguration.getSize() == PizzaSize.LARGE )
            maxToppingCount = 3;
        else
            maxToppingCount = 5;
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
    private void updateToppingSelectors( ToppingItem oldItem, ToppingItem newItem, ToppingSelector changeSelector )
    {
        for( ToppingSelector selector : toppingSelectors )
        {
            if( selector == changeSelector )
                continue;

            Collection<ToppingItem> toppingItemsOfSelector = selector.getToppingItems();
            if( oldItem != null ) toppingItemsOfSelector.add( oldItem );
            if( newItem != null ) toppingItemsOfSelector.remove( newItem );
        }

        if( oldItem != null ) toppingItems.add( oldItem );
        if( newItem != null ) toppingItems.remove( newItem );
    }
}
