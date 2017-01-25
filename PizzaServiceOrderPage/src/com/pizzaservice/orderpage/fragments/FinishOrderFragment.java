package com.pizzaservice.orderpage.fragments;

import com.pizzaservice.buissness_objects.*;
import com.pizzaservice.data_access_objects.CustomerDAO;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.OrderDAO;
import com.pizzaservice.data_access_objects.StoreDAO;
import com.pizzaservice.data_access_objects_impl.CustomerDatabaseDAO;
import com.pizzaservice.data_access_objects_impl.OrderDatabaseDAO;
import com.pizzaservice.data_access_objects_impl.StoreDatabaseDAO;
import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.fragment_fxml.FragmentURLs;
import com.pizzaservice.orderpage.items.PizzaConfigurationItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Collection;

/**
 * Created by philipp on 22.01.17.
 */
public class FinishOrderFragment extends Fragment
{
    @FXML
    TextField tfFirstName, tfSecondName, tfPhoneNumber;

    @FXML
    ChoiceBox cbStore;

    @FXML
    Text txtBill;

    private boolean billShowing;

    public FinishOrderFragment( Fragment oldFragment )
    {
        super( FragmentURLs.FINISH_ORDER, oldFragment );
    }

    @Override
    public void setup()
    {
        setupStoreChoiceBox();
        setupBill();
    }

    @FXML
    public void actionShowBill( ActionEvent actionEvent )
    {
        showBill( !billShowing );
    }

    @FXML
    public void actionSubmitOrder( ActionEvent actionEvent )
    {
        String firstName = tfFirstName.getText();
        String secondName = tfSecondName.getText();
        String phoneNumber = tfPhoneNumber.getText();
        Store store = (Store) cbStore.getSelectionModel().getSelectedItem();

        if( !checkInputs( firstName, secondName, phoneNumber, store ) )
            return;

        try
        {
            Customer customer = obtainCustomer( firstName, secondName, phoneNumber );
            if( customer == null )
                return;

            createOrder( customer, store );

            Utils.showInformationMessage( "Bestellung abgeschlossen!" );
            System.exit( 0 );
        }
        catch( DataAccessException e )
        {
            Utils.handleDataAccessException( e, this );
        }
    }

    private boolean checkInputs( String firstName, String secondName, String phoneNumber, Store store )
    {
        if( !firstName.matches("[a-zA-Z]+") )
        {
            Utils.showInputErrorMessage( "Ungültige Eingabe des Vornamens!" );
            return false;
        }

        if( !secondName.matches("[a-zA-Z]+") )
        {
            Utils.showInputErrorMessage( "Ungültige Eingabe des Nachnamens!" );
            return false;
        }

        if( !phoneNumber.trim().matches( "[0-9]+" ) )
        {
            Utils.showInputErrorMessage( "Ungültige Eingabe der Telefonnummer!" );
            return false;
        }

        if( store == null )
        {
            Utils.showInputErrorMessage( "Bitte wähle eine Filiale aus!" );
            return false;
        }

        return true;
    }

    private Customer obtainCustomer( String firstName, String secondName, String phoneNumber ) throws DataAccessException
    {
        CustomerDAO customerDAO = new CustomerDatabaseDAO( database );
        Customer customer = customerDAO.getCustomerByPhoneNumber( phoneNumber );
        // there isn't any customer with the same phone number yet?
        if( customer == null )
        {
            // create new customer and add it to the database
            customer = new Customer();
            customer.setFirstName( firstName );
            customer.setSecondName( secondName );
            customer.setPhoneNumber( phoneNumber );
            customer.setLocked( false );
            customer.setLockedUntil( null );

            customerDAO.addCustomer( customer );

            return customer;
        }

        // there is a customer with the same phone number - so check name equality
        if( customer.getFirstName() != firstName || customer.getSecondName() != secondName )
        {
            Utils.showInputErrorMessage( "Es existiert bereits ein anderer Kunde mit derselben Telefonnummer!" );
            return null;
        }

        return customer;
    }

    private void createOrder( Customer customer, Store store ) throws DataAccessException
    {
        Order order = new Order();
        order.setCustomer( customer );
        order.setStore( store );
        order.setState( OrderState.NEW );
        order.setDelivering( false );
        order.setCustomerAddress( null );
        order.setTimeAtStartOfDelivering( null );

        OrderDAO orderDAO = new OrderDatabaseDAO( database );
        orderDAO.addOrder( order );

        // TODO add pizza configurations to order
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent )
    {
        setNewFragment( new MainMenuFragment( this ) );
    }

    private void setupStoreChoiceBox()
    {
        try
        {
            StoreDAO storeDAO = new StoreDatabaseDAO( database );
            Collection<Store> stores = storeDAO.getStores();
            cbStore.setItems( FXCollections.observableArrayList( stores ) );
        }
        catch( DataAccessException e )
        {
            Utils.handleDataAccessException( e, this );
        }
    }

    private void setupBill()
    {
        String bill = "";
        for( PizzaConfiguration configuration : session.getPizzaConfigurations() )
        {
            PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( configuration );
            bill += configurationItem.toString() + "\n\n##############\n\n";
        }

        txtBill.setText( bill );
        showBill( false );
    }

    private void showBill( boolean show )
    {
        txtBill.setVisible( show );
        txtBill.setManaged( show );

        billShowing = show;
    }
}
