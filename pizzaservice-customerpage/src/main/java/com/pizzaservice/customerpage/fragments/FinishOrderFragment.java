package com.pizzaservice.customerpage.fragments;

import com.pizzaservice.api.buissness_objects.*;
import com.pizzaservice.api.data_access_objects.CustomerDAO;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.data_access_objects.OrderDAO;
import com.pizzaservice.api.data_access_objects.PizzaConfigurationDAO;
import com.pizzaservice.customerpage.MyUtils;
import com.pizzaservice.common.Utils;
import com.pizzaservice.common.items.PizzaConfigurationItem;
import com.pizzaservice.common.items.StoreItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by philipp on 22.01.17.
 */
public class FinishOrderFragment extends Fragment
{
    public static final int MAX_CHARACTERS = 45;

    @FXML TextField tfFirstName, tfSecondName, tfPhoneNumber;
    @FXML ChoiceBox<StoreItem> cbStore;
    @FXML Text txtBill;

    @FXML public void actionShowBill( ActionEvent actionEvent ) { toggleBill(); }
    @FXML public void actionSubmitOrder( ActionEvent actionEvent ) { submitOrder(); }
    @FXML public void actionAbort( ActionEvent actionEvent ) { abort(); }

    private boolean billShowing;

    public FinishOrderFragment( Fragment oldFragment )
    {
        super( "finish_order.fxml", oldFragment );
    }

    @Override
    public void setup()
    {
        setupStoreChoiceBox();
        setupBill();
    }

    public FinishOrderFragment enterFirstName( String firstName )
    {
        tfFirstName.setText( firstName );
        return this;
    }

    public FinishOrderFragment enterSecondName( String secondName )
    {
        tfSecondName.setText( secondName );
        return this;
    }

    public FinishOrderFragment enterPhoneNumber( String phoneName )
    {
        tfPhoneNumber.setText( phoneName );
        return this;
    }

    public FinishOrderFragment chooseStore( int storeIndex )
    {
        cbStore.getSelectionModel().select( storeIndex );
        return this;
    }

    public FinishOrderFragment toggleBill()
    {
        showBill( !billShowing );
        return this;
    }

    public void submitOrder()
    {
        String firstName = tfFirstName.getText();
        String secondName = tfSecondName.getText();
        String phoneNumber = tfPhoneNumber.getText().trim();
        StoreItem storeItem = cbStore.getValue();

        if( !checkInputs( firstName, secondName, phoneNumber, storeItem.getStore() ) )
            return;

        try
        {
            Customer customer = obtainCustomer( firstName, secondName, phoneNumber );
            if( customer == null )
                return;

            createOrder( customer, storeItem.getStore() );

            Utils.showInformationMessage( "Bestellung abgeschlossen!" );
            System.exit( 0 );
        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e, this );
        }
    }

    public Fragment abort()
    {
        return setNewFragment( new MainMenuFragment( this ) );
    }

    private boolean checkInputs( String firstName, String secondName, String phoneNumber, Store store )
    {
        if( !Utils.containsOnlyLetters( firstName ) )
        {
            Utils.showInputErrorMessage( "Ungültige Eingabe des Vornamens!" );
            return false;
        }

        if( !Utils.containsOnlyLetters( secondName ) )
        {
            Utils.showInputErrorMessage( "Ungültige Eingabe des Nachnamens!" );
            return false;
        }

        if( !phoneNumber.trim().matches( "[0-9]+" ) )
        {
            Utils.showInputErrorMessage( "Ungültige Eingabe der Telefonnummer!" );
            return false;
        }

        if( firstName.length() > MAX_CHARACTERS ||
            secondName.length() > MAX_CHARACTERS ||
            phoneNumber.length() > MAX_CHARACTERS )
        {
            Utils.showInputErrorMessage( "Jede Eingabe darf maximal " + MAX_CHARACTERS + " Zeichen enthalten!" );
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
        CustomerDAO customerDAO = daoBundle.getCustomerDAO();
        Customer customer = customerDAO.findCustomerByPhoneNumber( phoneNumber );
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
        if( !customer.getFirstName().equals( firstName ) || !customer.getSecondName().equals( secondName ) )
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
        order.setPizzaConfigurations( new ArrayList<>() );

        // add the new order into the database
        OrderDAO orderDAO = daoBundle.getOrderDAO();
        orderDAO.addOrder( order );

        // add the pizza configurations into the database
        PizzaConfigurationDAO pizzaConfigurationDAO = daoBundle.getPizzaConfigurationDAO();

        for( PizzaConfiguration pizzaConfiguration : session.getPizzaConfigurations() )
        {
            pizzaConfiguration.setOrder( order );
            pizzaConfigurationDAO.addPizzaConfiguration( pizzaConfiguration );
        }
    }

    private void setupStoreChoiceBox()
    {
        try
        {
            StoreItem.setupChoiceBox( cbStore, daoBundle );
        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e, this );
        }
    }

    private void setupBill()
    {
        String bill = "";
        float totalPrice = 0;

        for( PizzaConfiguration configuration : session.getPizzaConfigurations() )
        {
            PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( configuration );
            bill += configurationItem.toString() + "\n\n====================\n\n";
            totalPrice += configurationItem.getTotalPrice();
        }

        bill += "####################\n";
        bill += "Gesamtpreis: " + String.format( "%.2f", totalPrice ) + "€\n";
        bill += "####################";

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
