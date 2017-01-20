package com.pizzaservice.orderpage.controller;

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
    public void actionFinishOrder( ActionEvent actionEvent )
    {

    }

    @FXML
    public void actionAbortOrder( ActionEvent actionEvent )
    {
    }
}
