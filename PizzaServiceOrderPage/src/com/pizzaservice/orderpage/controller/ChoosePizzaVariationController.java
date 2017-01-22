package com.pizzaservice.orderpage.controller;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.buissness_objects.PizzaSize;
import com.pizzaservice.buissness_objects.PizzaVariation;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.PizzaVariationDAO;
import com.pizzaservice.data_access_objects_impl.PizzaVariationDatabaseDAO;
import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.fragment.FragmentURLs;
import com.pizzaservice.orderpage.items.PizzaVariationItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by philipp on 17.01.17.
 */
public class ChoosePizzaVariationController extends Controller
{
    @FXML
    ChoiceBox cbPizzaVariation1;

    @FXML
    ChoiceBox cbPizzaVariation2;

    @Override
    protected void onSetup()
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
        this.<MainMenuController>setFragment( FragmentURLs.MAIN_MENU );
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

        this.<ChooseToppingsController>setFragment( FragmentURLs.CHOOSE_TOPPINGS );
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
