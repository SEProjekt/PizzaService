package com.pizzaservice.orderpage;

import com.pizzaservice.db.ConnectionParams;
import com.pizzaservice.db.Database;
import com.pizzaservice.orderpage.controller.MainMenuController;
import com.pizzaservice.orderpage.fragment.FragmentURLs;
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
    public static final String userName = "root";
    public static final String password = ""; // enter it!
    public static final String dbms = "mysql";
    public static final String serverName = "localhost";
    public static final String portNumber = "3306";
    public static final String databaseName = "PizzaService";
    public static final String googleAPIKey = "AIzaSyAqM1vOIcpK9V1CMPQSjUg6QAuRA6jgzaM";

    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        Parent root = FXMLLoader.load( getClass().getResource( "main.fxml" ) );
        root.getStylesheets().add( this.getClass().getResource( "style.css" ).toExternalForm() );
        primaryStage.setTitle( "Pizza Bestellungsseite" );
        primaryStage.setScene( new Scene( root, 400, 300 ) );
        primaryStage.show();
    }

    @FXML
    Pane rootPane;

    @FXML
    public void initialize() throws IOException
    {
        FXMLLoader loader = new FXMLLoader( FragmentURLs.MAIN_MENU );
        ConnectionParams params = new ConnectionParams( userName, password, dbms, serverName, portNumber, databaseName );
        Database db = new Database( params );

        rootPane.getChildren().clear();
        rootPane.getChildren().add( loader.load() );

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setup( rootPane, new Session(), db );
    }

    public static void main( String[] args )
    {
        launch( args );
    }
}