package com.pizzaservice.orderpage.fragments;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.buissness_objects.PizzaVariation;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.PizzaVariationDAO;
import com.pizzaservice.data_access_objects_impl.PizzaVariationDatabaseDAO;
import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.fragment_fxml.FragmentURLs;
import com.pizzaservice.orderpage.items.PizzaVariationItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by philipp on 17.01.17.
 */
public class ChoosePizzaVariationFragment extends Fragment
{
    @FXML
    ChoiceBox cbPizzaVariation1;

    @FXML
    ChoiceBox cbPizzaVariation2;

    public ChoosePizzaVariationFragment( Fragment oldFragment, boolean split )
    {
        super( split ? FragmentURLs.CHOOSE_PIZZA_VARIATIONS : FragmentURLs.CHOOSE_PIZZA_VARIATION, oldFragment );
    }

    @Override
    protected void onLoadFinished()
    {
        try
        {
            // get the pizza variations and create a list of items to display name and price in the choice box
            Collection<PizzaVariationItem> variationItems = getPizzaVariationItems();
            cbPizzaVariation1.setItems( FXCollections.observableArrayList( variationItems ) );

            // if split - set items for second choice box
            if( session.getCurrentPizzaConfiguration().isSplit() )
                cbPizzaVariation2.setItems( FXCollections.observableArrayList( variationItems ) );

        }
        catch( DataAccessException e )
        {
            Utils.showErrorMessage( e.getMessage() );
        }
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        setNewFragment( new MainMenuFragment( this ) );
    }

    @FXML
    public void actionNext( ActionEvent actionEvent ) throws IOException
    {
        PizzaVariationItem selectedItem1 = (PizzaVariationItem) cbPizzaVariation1.getSelectionModel().getSelectedItem();
        if( selectedItem1 == null )
        {
            Utils.showErrorMessage( "Es wurde noch keine Variation ausgewählt!" );
            return;
        }

        PizzaConfiguration currentConfiguration = session.getCurrentPizzaConfiguration();
        currentConfiguration.setPizzaVariation1( selectedItem1.getPizzaVariation() );

        if( currentConfiguration.isSplit() )
        {
            PizzaVariationItem selectedItem2 = (PizzaVariationItem) cbPizzaVariation2.getSelectionModel().getSelectedItem();
            if( selectedItem2 == null )
            {
                Utils.showErrorMessage( "Es wurde noch keine Variation ausgewählt!" );
                return;
            }

            currentConfiguration.setPizzaVariation2( selectedItem2.getPizzaVariation() );
        }

        setNewFragment( new ChooseToppingsFragment( this ) );
    }

    private Collection<PizzaVariationItem> getPizzaVariationItems() throws DataAccessException
    {
        PizzaVariationDAO pizzaVariationDAO = new PizzaVariationDatabaseDAO( database );
        Collection<PizzaVariation> variations = pizzaVariationDAO.getPizzaVariations();
        Collection<PizzaVariationItem> variationItems = new ArrayList<>();
        for( PizzaVariation variation : variations )
        {
            PizzaVariationItem variationItem = new PizzaVariationItem(
                variation, session.getCurrentPizzaConfiguration().getSize() );
            variationItems.add( variationItem );
        }

        return variationItems;
    }
}
