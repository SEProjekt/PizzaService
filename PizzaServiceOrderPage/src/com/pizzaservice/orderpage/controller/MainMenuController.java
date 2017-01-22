package com.pizzaservice.orderpage.controller;

import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.fragment.FragmentURLs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenuController extends Controller
{
    @Override
    protected void onSetup()
    {

    }

    @FXML
    public void actionAddPizza( ActionEvent actionEvent ) throws IOException
    {
        this.<ChoosePizzaSizeController>setFragment( FragmentURLs.CHOOSE_PIZZA_SIZE );
    }

    @FXML
    public void actionShowCart( ActionEvent actionEvent ) throws IOException
    {
        if( session.getOrder().getPizzaConfigurations().isEmpty() )
        {
            Utils.showErrorMessage( "Es befindet sich keine Pizza im Warenkorb!" );
            return;
        }

        this.<ShowCartController>setFragment( FragmentURLs.SHOW_CART );
    }

    @FXML
    public void actionFinishOrder( ActionEvent actionEvent ) throws IOException
    {
        this.<FinishOrderController>setFragment( FragmentURLs.FINISH_ORDER );
    }

    @FXML
    public void actionAbortOrder( ActionEvent actionEvent )
    {
        System.exit( 0 );
    }
}
