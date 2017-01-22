package com.pizzaservice.orderpage.controller;

import com.pizzaservice.orderpage.fragment.FragmentURLs;
import com.pizzaservice.orderpage.items.PizzaConfigurationItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Created by philipp on 20.01.17.
 */
public class FinishPizzaConfigurationController extends Controller
{
    @FXML
    Label lbConfiguration;

    @Override
    protected void onSetup()
    {
        PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( session.getCurrentPizzaConfiguration() );
        lbConfiguration.setText( configurationItem.toString() );
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        this.<MainMenuController>setFragment( FragmentURLs.MAIN_MENU );
    }

    @FXML
    public void actionFinish( ActionEvent actionEvent ) throws IOException
    {
        session.getOrder().getPizzaConfigurations().add( session.getCurrentPizzaConfiguration() );
        this.<MainMenuController>setFragment( FragmentURLs.MAIN_MENU );
    }
}
