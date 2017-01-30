package com.pizzaservice.api.database_data_access_objects;

import com.pizzaservice.api.data_access_objects.CustomerDAO;
import com.pizzaservice.api.data_access_objects.DAOBundle;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.buissness_objects.Customer;
import com.pizzaservice.api.db.Database;
import com.pizzaservice.api.db.Row;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by philipp on 24.01.17.
 */
public class CustomerDatabaseDAO extends DatabaseDAO implements CustomerDAO
{
    public static final int COLUMN_ID = 1;
    public static final int COLUMN_FIRST_NAME = 2;
    public static final int COLUMN_SECOND_NAME = 3;
    public static final int COLUMN_PHONE_NUMBER = 4;
    public static final int COLUMN_LOCKED = 5;
    public static final int COLUMN_LOCKED_UNTIL = 6;

    private static int queryCounter = 0;

    private Hashtable<Long, Customer> cache = new Hashtable<>();

    public CustomerDatabaseDAO( Database database, DatabaseDAOBundle databaseDAOBundle )
    {
        super( database, databaseDAOBundle );
    }

    @Override
    public boolean addCustomer( Customer customer ) throws DataAccessException
    {
        try
        {
            // check if a customer with that phone number already exists
            if( findCustomerByPhoneNumber( customer.getPhoneNumber() ) != null )
                return false;

            String insert = "INSERT INTO customers (first_name, second_name, phone_number, locked, locked_until) VALUES( ?, ?, ?, ?, ? )";
            List<Object> args = new ArrayList<>();
            args.add( customer.getFirstName() );
            args.add( customer.getSecondName() );
            args.add( customer.getPhoneNumber() );
            args.add( customer.isLocked() );
            args.add( customer.getLockedUntil() );
            database.update( insert, args, row -> customer.setId( row.getLong( 1 ) ) );

            return true;
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public Collection<Customer> getCustomers() throws DataAccessException
    {
        try
        {
            System.out.println( "Customer query number: " + ++queryCounter );

            cache = new Hashtable<>();
            Collection<Customer> customers = new ArrayList<>();

            String query = "SELECT * FROM customers";
            database.query( query, new ArrayList<>(), row ->
            {
                Customer customer = new Customer();
                processCustomer( customer, row );

                customers.add( customer );
                cache.put( customer.getId(), customer );
            } );

            return customers;
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public Customer findCustomerByPhoneNumber( String phoneNumber ) throws DataAccessException
    {
        // Note: It should always be the case that there is only one row with the given phone number at max
        // so 'LIMIT 1' is a bit unnecessary.
        String query = "SELECT * FROM customers WHERE phone_number = ? LIMIT 1";
        List<Object> args = new ArrayList<>();
        args.add( phoneNumber );
        return findCustomerByQuery( query, args );
    }

    @Override
    public Customer findCustomerById( long id ) throws DataAccessException
    {
        if( id == 0 )
            return null;

        Customer cachedCustomer = cache.get( id );
        if( cachedCustomer != null )
            return cachedCustomer;

        String query = "SELECT * FROM customers where id = ?";
        List<Object> args = new ArrayList<>();
        args.add( id );
        return findCustomerByQuery( query, args );
    }

    private Customer findCustomerByQuery( String query, List<Object> args ) throws DataAccessException
    {
        System.out.println( "Customer query number: " + ++queryCounter );

        try
        {
            Customer customer = new Customer();

            boolean found = database.query( query, args, row -> processCustomer( customer, row ) );

            if( !found )
                return null;

            return customer;
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    private void processCustomer( Customer customer, Row row ) throws SQLException
    {
        customer.setId( row.getLong( COLUMN_ID ) );
        customer.setFirstName( row.getString( COLUMN_FIRST_NAME ) );
        customer.setSecondName( row.getString( COLUMN_SECOND_NAME ) );
        customer.setPhoneNumber( row.getString( COLUMN_PHONE_NUMBER ) );
        customer.setLocked( row.getBoolean( COLUMN_LOCKED ) );
        customer.setLockedUntil( row.getTime( COLUMN_LOCKED_UNTIL ) );
    }
}
