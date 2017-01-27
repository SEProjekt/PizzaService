package com.pizzaservice.kitchenpage;

import com.pizzaservice.common.Utils;
import com.pizzaservice.api.db.Database;
import com.pizzaservice.kitchenpage.fragments.ChooseStoreFragment;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by philipp on 26.01.17.
 */
public class Main extends Application
{
    @FXML
    Pane contentPane;

    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        Parent root = FXMLLoader.load( getClass().getResource( "main.fxml" ) );
        root.getStylesheets().add( this.getClass().getResource( "styles/style.css" ).toExternalForm() );
        primaryStage.setTitle( "Pizza Service Bestellungsanzeige" );
        primaryStage.setScene( new Scene( root, 800, 600 ) );
        primaryStage.show();
    }

    @FXML
    public void initialize()
    {
        Database db = new Database( Utils.getConnectionParams() );

        ChooseStoreFragment chooseStoreFragment = new ChooseStoreFragment( contentPane, new Session(), db );

        contentPane.getChildren().clear();
        contentPane.getChildren().add( chooseStoreFragment );

        chooseStoreFragment.setup();
    }

    public static void main( String[] args )
    {
        launch( args );
    }
}
