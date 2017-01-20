package com.pizzaservice.orderpage.controller;

import com.pizzaservice.orderpage.fragment.FragmentURLs;
import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.buissness_objects.PizzaSize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

/**
 * Created by philipp on 16.01.17.
 */
public class ChoosePizzaSizeController extends Controller
{
    private ToggleGroup sizeGroup;

    @FXML
    RadioButton radSizeSmall;
    @FXML
    RadioButton radSizeLarge;
    @FXML
    RadioButton radSizeXLarge;

    @Override
    protected void onSetup()
    {
        sizeGroup = new ToggleGroup();
        radSizeSmall.setToggleGroup( sizeGroup );
        radSizeLarge.setToggleGroup( sizeGroup );
        radSizeXLarge.setToggleGroup( sizeGroup );
        radSizeSmall.setSelected( true );
    }

    @FXML
    public void actionNext( ActionEvent actionEvent ) throws IOException
    {
        PizzaConfiguration pizzaConfiguration = new PizzaConfiguration();
        session.setCurrentPizzaConfiguration( pizzaConfiguration );

        if( radSizeSmall.isSelected() )
        {
            pizzaConfiguration.setSize( PizzaSize.SMALL );

            this.<ChoosePizzaVariationController>setFragment( FragmentURLs.CHOOSE_PIZZA_VARIATION );
        }
        else
        {
            if( radSizeLarge.isSelected() )
                pizzaConfiguration.setSize( PizzaSize.LARGE );
            else
                pizzaConfiguration.setSize( PizzaSize.X_LARGE );

            this.<ChooseSplitController>setFragment( FragmentURLs.CHOOSE_SPLIT );
        }
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        this.<MainMenuController>setFragment( FragmentURLs.MAIN_MENU );
    }
}
