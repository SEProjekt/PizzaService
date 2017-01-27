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
    @FXML Label lbConfiguration;

    @FXML public void actionFinish( ActionEvent actionEvent ) { finish(); }
    @FXML public void actionAbort( ActionEvent actionEvent )
    {
        abort();
    }

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

    public Fragment finish()
    {
        session.getPizzaConfigurations().add( session.getCurrentPizzaConfiguration() );
        return setNewFragment( new MainMenuFragment( this ) );
    }

    public Fragment abort()
    {
        return setNewFragment( new MainMenuFragment( this ) );
    }
}
