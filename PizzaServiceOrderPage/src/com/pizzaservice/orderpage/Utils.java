package com.pizzaservice.orderpage;

import javafx.scene.control.Alert;

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
}
