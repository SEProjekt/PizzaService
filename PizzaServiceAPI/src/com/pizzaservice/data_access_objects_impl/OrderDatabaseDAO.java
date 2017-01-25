package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.buissness_objects.*;
import com.pizzaservice.data_access_objects.CustomerDAO;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.OrderDAO;
import com.pizzaservice.db.Database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 24.01.17.
 */
public class OrderDatabaseDAO extends DatabaseDAO implements OrderDAO
{
    public static final int COLUMN_ID = 1;
    public static final int COLUMN_ID_CUSTOMER = 2;
    public static final int COLUMN_ID_STORE = 3;
    public static final int COLUMN_STATE = 4;
    public static final int COLUMN_DELIVERING = 5;
    public static final int COLUMN_STREET = 6;
    public static final int COLUMN_HOUSE_NUMBER = 7;
    public static final int COLUMN_POSTCODE = 8;
    public static final int COLUMN_CITY = 9;
    public static final int COLUMN_COUNTRY = 10;
    public static final int COLUMN_TIME_AT_START_OF_DELIVERING = 11;

    public OrderDatabaseDAO( Database database )
    {
        super( database );
    }

    @Override
    public void addOrder( Order order ) throws DataAccessException
    {
        try
        {
            String insert =
                "INSERT INTO orders " +
                "(id_customer, id_store, state, delivering, street, house_number, postcode, city, country, time_at_start_of_delivering) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            List<Object> args = new ArrayList<>();
            args.add( order.getCustomer().getId() );
            args.add( order.getStore().getId() );
            args.add( order.getState().toInt() );
            args.add( order.isDelivering() );
            if( order.getCustomerAddress() == null )
            {
                args.add( null );
                args.add( null );
                args.add( null );
                args.add( null );
                args.add( null );
            }
            else
            {
                args.add( order.getCustomerAddress().getStreet() );
                args.add( order.getCustomerAddress().getHouseNumber() );
                args.add( order.getCustomerAddress().getPostcode() );
                args.add( order.getCustomerAddress().getCity() );
                args.add( order.getCustomerAddress().getCountry() );
            }
            args.add( order.getTimeAtStartOfDelivering() );

            database.update( insert, args, row -> order.setId( row.getLong( 1 ) ) );
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public void getOrdersOfStore( Store store ) throws DataAccessException
    {
        try
        {
            Collection<Order> orders = new ArrayList<>();

            String query = "SELECT * FROM orders WHERE id_store = ?";
            List<Object> args = new ArrayList<>();
            args.add( store.getId() );
            database.query( query, args, row ->
            {
                Order order = new Order();

                long idCustomer = row.getLong( COLUMN_ID_CUSTOMER );
                CustomerDAO customerDAO = new CustomerDatabaseDAO( database );
                Customer customer = customerDAO.findCustomerById( idCustomer );
                if( customer == null )
                    throw new DataAccessException( this, "Cannot find id_customer: " + idCustomer + "!" );

                order.setCustomer( customer );
                order.setStore( store );
                order.setState( OrderState.fromInt( row.getInt( COLUMN_STATE ) ) );
                order.setDelivering( row.getBoolean( COLUMN_DELIVERING ) );

                Address address = new Address();
                address.setStreet( row.getString( COLUMN_STREET ) );
                address.setHouseNumber( row.getString( COLUMN_HOUSE_NUMBER ) );
                address.setPostcode( row.getString( COLUMN_POSTCODE ) );
                address.setCity( row.getString( COLUMN_CITY ) );
                address.setCountry( row.getString( COLUMN_COUNTRY ) );

                order.setCustomerAddress( address );
                order.setTimeAtStartOfDelivering( row.getTime( COLUMN_TIME_AT_START_OF_DELIVERING ) );

                new PizzaConfigurationDatabaseDAO( database ).getPizzaConfigurationsOfOrder( order );

                orders.add( order );
            } );

            store.setOrders( orders );
        }
        catch( Exception e ) { throw handleException( e ); }
    }
}
