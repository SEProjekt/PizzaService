package com.pizzaservice.kitchenpage;

import com.pizzaservice.api.data_access_objects.DAOBundle;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.data_access_objects_impl.DatabaseDAOBundle;
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
        Database database = new Database( Utils.getConnectionParams() );
        DAOBundle daoBundle = new DatabaseDAOBundle( database );

        loadCaches( daoBundle );

        ChooseStoreFragment chooseStoreFragment = new ChooseStoreFragment( contentPane, new Session(), daoBundle );

        contentPane.getChildren().clear();
        contentPane.getChildren().add( chooseStoreFragment );

        chooseStoreFragment.setup();
    }

    /**
     * This method prepares for database access by calling get functions on data access objects
     * which will cache the results. By doing so it will reduce the number of future round trip times.
     * @param daoBundle
     */
    private void loadCaches( DAOBundle daoBundle )
    {
        try
        {
            daoBundle.getCustomerDAO().getCustomers();
            daoBundle.getToppingDAO().getToppings();
            daoBundle.getPizzaVariationDAO().getPizzaVariations();
        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e );
        }
    }

    public static void main( String[] args )
    {
        launch( args );
    }
}
