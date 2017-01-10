package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main extends Application
{
    private static final String userName = "root";
    private static final String password = "m13u21m13PPILIHP";
    private static final String dbms = "mysql";
    private static final String serverName = "localhost";
    private static final String portNumber = "3306";

    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        Parent root = FXMLLoader.load( getClass().getResource( "sample.fxml" ) );
        primaryStage.setTitle( "Hello World" );
        primaryStage.setScene( new Scene( root, 300, 275 ) );
        primaryStage.show();
    }

    private static void setupDatabase()
    {
        try
        {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
        }
        catch( Exception e )
        {
            System.out.println( "Unable to instantiate jdbc driver!" );
        }

        try
        {
            Properties connectionProps = new Properties();
            connectionProps.put( "user", userName );
            connectionProps.put( "password", password );

            Connection conn = DriverManager.getConnection( "jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/", connectionProps );
        }
        catch( SQLException ex )
        {
            // handle any errors
            System.out.println( "SQLException: " + ex.getMessage() );
            System.out.println( "SQLState: " + ex.getSQLState() );
            System.out.println( "VendorError: " + ex.getErrorCode() );
        }
    }

    public static void main( String[] args )
    {
        setupDatabase();
        launch( args );
    }
}
