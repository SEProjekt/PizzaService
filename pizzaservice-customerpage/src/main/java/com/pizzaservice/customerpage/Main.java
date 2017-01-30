package com.pizzaservice.customerpage;

import com.pizzaservice.api.data_access_objects.DAOBundle;
import com.pizzaservice.api.database_data_access_objects.DatabaseDAOBundle;
import com.pizzaservice.common.Utils;
import com.pizzaservice.api.db.Database;
import com.pizzaservice.customerpage.fragments.MainMenuFragment;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application
{
    @FXML
    Pane contentPane;

    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        Parent root = FXMLLoader.load( getClass().getResource( "main.fxml" ) );
        root.getStylesheets().add( this.getClass().getResource( "styles/style.css" ).toExternalForm() );
        primaryStage.setTitle( "Pizza Bestellungsseite" );
        primaryStage.setScene( new Scene( root, 400, 350 ) );
        primaryStage.show();
    }

    @FXML
    public void initialize()
    {
        Database database = new Database( Utils.getConnectionParams() );
        DAOBundle daoBundle = new DatabaseDAOBundle( database );

        MainMenuFragment mainMenuFragment = new MainMenuFragment( contentPane, new Session(), daoBundle );

        contentPane.getChildren().clear();
        contentPane.getChildren().add( mainMenuFragment );

        mainMenuFragment.setup();
    }

    public static void main( String[] args )
    {
        launch( args );
    }
}
