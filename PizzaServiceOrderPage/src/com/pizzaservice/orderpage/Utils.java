package com.pizzaservice.orderpage;

import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.orderpage.fragments.Fragment;
import com.pizzaservice.orderpage.fragments.MainMenuFragment;
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
     * Shows Error dialog and returns to main menu.
     * @param e
     * @param currentFragment
     */
    public static void handleDataAccessException( DataAccessException e, Fragment currentFragment )
    {
        showConnectionErrorMessage( e.getMessage() );
        currentFragment.setNewFragment( new MainMenuFragment( currentFragment ) );
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
