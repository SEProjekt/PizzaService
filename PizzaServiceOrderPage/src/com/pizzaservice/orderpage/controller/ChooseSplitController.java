package com.pizzaservice.orderpage.controller;

import com.pizzaservice.orderpage.fragment.FragmentURLs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

/**
 * Created by philipp on 17.01.17.
 */
public class ChooseSplitController extends Controller
{
    private ToggleGroup splitGroup;

    @FXML
    RadioButton radSplitYes;

    @FXML
    RadioButton radSplitNo;

    @Override
    protected void onSetup()
    {
        splitGroup = new ToggleGroup();
        radSplitYes.setToggleGroup( splitGroup );
        radSplitNo.setToggleGroup( splitGroup );
        radSplitNo.setSelected( true );
    }

    @FXML
    public void actionNext( ActionEvent actionEvent ) throws IOException
    {
        if( radSplitYes.isSelected() )
        {
            session.getCurrentPizzaConfiguration().setSplit( true );
            this.<ChoosePizzaVariationController>setFragment( FragmentURLs.CHOOSE_PIZZA_VARIATIONS );
        }
        else
        {
            session.getCurrentPizzaConfiguration().setSplit( false );
            this.<ChoosePizzaVariationController>setFragment( FragmentURLs.CHOOSE_PIZZA_VARIATION );
        }
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        this.<MainMenuController>setFragment( FragmentURLs.MAIN_MENU );
    }
}
