package com.pizzaservice.orderpage.controller;

import com.pizzaservice.buissness_objects.PizzaVariation;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.PizzaVariationDAO;
import com.pizzaservice.data_access_objects_impl.PizzaVariationDatabaseDAO;
import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.fragment.FragmentURLs;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

/**
 * Created by philipp on 17.01.17.
 */
public class ChoosePizzaVariationController extends Controller
{
    @FXML
    ChoiceBox cbPizzaVariation;

    @Override
    protected void onSetup()
    {
        PizzaVariationDAO pizzaVariationDAO = new PizzaVariationDatabaseDAO( database );

        try
        {
            cbPizzaVariation.setItems( FXCollections.observableArrayList( pizzaVariationDAO.getPizzaVariations() ) );
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
        PizzaVariation selectedVariation = (PizzaVariation) cbPizzaVariation.getSelectionModel().getSelectedItem();

        if( selectedVariation == null )
        {
            Utils.showErrorMessage( "Es wurde noch keine Variation ausgewählt!" );
            return;
        }

        session.getCurrentPizzaConfiguration().setPizzaVariation1( selectedVariation );

        this.<ChooseToppingsController>setFragment( FragmentURLs.CHOOSE_TOPPINGS );
    }
}
