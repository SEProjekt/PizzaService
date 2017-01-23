package com.pizzaservice.orderpage;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;

/**
 * Created by philipp on 18.01.17.
 */
public class Utils
{
    public static void showErrorMessage( String message )
    {
        Alert alert = new Alert( Alert.AlertType.ERROR );
        alert.setHeaderText( "Fehler!" );
        alert.setContentText( message );
        alert.setResizable( true );
        alert.showAndWait();
    }

    /**
     * Loads a resource (.fxml) and sets its root and controller to object.
     * @param resource
     * @param object
     */
    public static void load( URL resource, Object object )
    {
        FXMLLoader loader = new FXMLLoader( resource );

        loader.setRoot( object );
        loader.setController( object );

        try
        {
            loader.load();
        }
        catch( IOException e )
        {
            throw new RuntimeException( e );
        }
    }
}
