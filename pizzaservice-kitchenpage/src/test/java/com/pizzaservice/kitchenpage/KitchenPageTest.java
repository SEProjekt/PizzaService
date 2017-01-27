package com.pizzaservice.kitchenpage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

/**
 * Created by philipp on 27.01.17.
 */
public class KitchenPageTest extends GuiTest
{
    @Override
    protected Parent getRootNode()
    {
        Parent parent = null;

        try
        {
            parent = FXMLLoader.load( getClass().getResource( "main.fxml" ) );
        }
        catch( IOException e ) {}

        return parent;
    }

    // TODO: add Tests!
}
