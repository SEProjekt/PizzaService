package com.pizzaservice.common;

import com.pizzaservice.api.db.ConnectionParams;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;

/**
 * Created by philipp on 18.01.17.
 */
public class Utils
{
    public static void showInputErrorMessage( String message )
    {
        Alert alert = new Alert( Alert.AlertType.WARNING );
        alert.setHeaderText( "Warnung!" );
        alert.setContentText( message );
        alert.setResizable( true );
        alert.showAndWait();
    }

    public static void showConnectionErrorMessage( String message )
    {
        Alert alert = new Alert( Alert.AlertType.ERROR );
        alert.setHeaderText( "Verbindungsproblem!" );
        alert.setContentText( message );
        alert.setResizable( true );
        alert.showAndWait();
    }

    public static void showInformationMessage( String message )
    {
        Alert alert = new Alert( Alert.AlertType.INFORMATION );
        alert.setHeaderText( "Information" );
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
        try
        {
            FXMLLoader loader = new FXMLLoader( resource );

            loader.setRoot( object );
            loader.setController( object );

            loader.load();
        }
        catch( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    public static boolean containsOnlyLetters( String str )
    {
        for( char c : str.toCharArray() )
        {
            if( !Character.isLetter( c ) )
                return false;
        }

        return true;
    }

    public static ConnectionParams getConnectionParams()
    {
        // local test server
//        return new ConnectionParams(
//            "PizzaServiceUser",
//            "PizzaService",
//            "mysql",
//            "localhost",
//            "3306",
//            "PizzaService"
//        );

        return new ConnectionParams(
            "PizzaService",
            "d4sG3h31m3SEP4ssw0rd",
            "mysql",
            "37.120.168.33",
            "3306",
            "PizzaService"
        );
    }
}
