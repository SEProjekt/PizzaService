package com.pizzaservice.customerpage.fragments;

import com.pizzaservice.common.items.PizzaConfigurationItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Created by philipp on 20.01.17.
 */
public class FinishPizzaConfigurationFragment extends Fragment
{
    @FXML
    Label lbConfiguration;

    public FinishPizzaConfigurationFragment( Fragment oldFragment )
    {
        super( "finish_pizza_configuration.fxml", oldFragment );
    }

    @Override
    public void setup()
    {
        PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( session.getCurrentPizzaConfiguration() );
        lbConfiguration.setText( configurationItem.toString() );
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        setNewFragment( new MainMenuFragment( this ) );
    }

    @FXML
    public void actionFinish( ActionEvent actionEvent ) throws IOException
    {
        session.getPizzaConfigurations().add( session.getCurrentPizzaConfiguration() );
        setNewFragment( new MainMenuFragment( this ) );
    }
}
