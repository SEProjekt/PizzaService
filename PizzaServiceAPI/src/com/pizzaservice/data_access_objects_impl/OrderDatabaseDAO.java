package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.buissness_objects.Order;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.OrderDAO;
import com.pizzaservice.db.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 24.01.17.
 */
public class OrderDatabaseDAO extends DatabaseDAO implements OrderDAO
{
    public OrderDatabaseDAO( Database database )
    {
        super( database );
    }

    @Override
    public void addOrder( Order order ) throws DataAccessException
    {
        try
        {
            String query =
                "INSERT INTO orders " +
                "(id_customer, id_store, state, delivering, street, house_number, postcode, city, country, time_at_start_of_delivering) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            List<Object> args = new ArrayList<>();
            args.add( order.getCustomer().getId() );
            args.add( order.getStore().getId() );
            args.add( order.getState().toInt() );
            args.add( order.isDelivering() );
            if( order.getCustomerAddress() != null )
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

            database.query( query, args, row -> order.setId( row.getLong( 1 ) ) );
        }
        catch( Exception e ) { handleException( e ); }
    }
}
