package com.pizzaservice.orderpage.controller;

import com.pizzaservice.orderpage.fragment.FragmentURLs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Created by philipp on 17.01.17.
 */
public class ChooseSplitController extends Controller
{
    @Override
    protected void onSetup()
    {

    }

    @FXML
    public void actionNext( ActionEvent actionEvent )
    {

    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        this.<MainMenuController>setFragment( FragmentURLs.MAIN_MENU );
    }
}
