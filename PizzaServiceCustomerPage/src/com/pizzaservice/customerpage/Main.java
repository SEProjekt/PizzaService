package com.pizzaservice.customerpage;

import com.pizzaservice.common.Utils;
import com.pizzaservice.db.ConnectionParams;
import com.pizzaservice.db.Database;
import com.pizzaservice.customerpage.fragments.MainMenuFragment;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @FXML
    Pane rootPane;

    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        Parent root = FXMLLoader.load( getClass().getResource( "main.fxml" ) );
        root.getStylesheets().add( this.getClass().getResource( "style.css" ).toExternalForm() );
        primaryStage.setTitle( "Pizza Bestellungsseite" );
        primaryStage.setScene( new Scene( root, 400, 350 ) );
        primaryStage.show();
    }

    @FXML
    public void initialize() throws IOException
    {
        Database db = new Database( Utils.getConnectionParams() );

        MainMenuFragment mainMenuFragment = new MainMenuFragment( rootPane, new Session(), db );

        rootPane.getChildren().clear();
        rootPane.getChildren().add( mainMenuFragment );

        mainMenuFragment.setup();
    }

    public static void main( String[] args )
    {
        launch( args );
    }
}
