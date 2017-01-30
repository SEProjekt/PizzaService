package com.pizzaservice.api.database_data_access_objects;

import com.pizzaservice.api.buissness_objects.*;
import com.pizzaservice.api.data_access_objects.OrderDAO;
import com.pizzaservice.api.db.Row;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.db.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by philipp on 24.01.17.
 */
public class OrderDatabaseDAO extends DatabaseDAO implements OrderDAO
{
    private static int queryCounter = 0;

    public OrderDatabaseDAO( Database database, DatabaseDAOBundle databaseDAOBundle )
    {
        super( database, databaseDAOBundle );
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

            database.update(
                insert,
                createInsertArgs( order ),
                row -> order.setId( row.getLong( 1 ) )
            );
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public void updateOrder( Order order ) throws DataAccessException
    {
        try
        {
            String update =
                "UPDATE orders " +
                "SET " +
                "id_customer = ?, " +
                "id_store = ?, " +
                "state = ?, " +
                "delivering = ?, " +
                "street = ?, " +
                "house_number = ?, " +
                "postcode = ?, " +
                "city = ?, " +
                "country = ?, " +
                "time_at_start_of_delivering = ? " +
                "WHERE id = ?";

            List<Object> args = createInsertArgs( order );
            args.add( order.getId() );

            database.update( update, args, row -> {} );
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public void getOrdersOfStore( Store store ) throws DataAccessException
    {
        System.out.println( "Order query number: " + ++queryCounter );

        try
        {
            Hashtable<Long, Order> orders = new Hashtable<>();

            String query =
                "SELECT pizza_configurations.id, pizza_configurations.id_order, pizza_configurations.size, " +
                "pizza_configurations.split, pizza_configurations.id_pizza_variation_1, pizza_configurations.id_pizza_variation_2, " +
                "pizza_configurations.id_topping_1, pizza_configurations.id_topping_2, pizza_configurations.id_topping_3, " +
                "pizza_configurations.id_topping_4, pizza_configurations.id_topping_5, " +
                "orders.id, orders.id_customer, orders.id_store, orders.state, orders.delivering, orders.street, " +
                "orders.house_number, orders.postcode, orders.city, orders.country, orders.time_at_start_of_delivering " +
                "FROM pizza_configurations " +
                "INNER JOIN orders ON orders.id = pizza_configurations.id_order " +
                "WHERE orders.id_store = ?";

            List<Object> args = new ArrayList<>();
            args.add( store.getId() );

            database.query( query, args, row ->
            {
                long idOrder = row.getLong( "orders.id" );
                Order order = orders.get( idOrder );
                if( order == null )
                {
                    order = new Order();
                    processOrder( order, store, new ArrayList<>(), row );
                    orders.put( order.getId(), order );
                }

                PizzaConfiguration pizzaConfiguration = new PizzaConfiguration();
                PizzaConfigurationDatabaseDAO pizzaConfigurationDatabaseDAO = databaseDAOBundle.getPizzaConfigurationDatabaseDAO();
                pizzaConfigurationDatabaseDAO.processPizzaConfiguration( pizzaConfiguration, order, row );
                order.getPizzaConfigurations().add( pizzaConfiguration );
            } );

            store.setOrders( orders.values() );
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    public void processOrder( Order order,
                              Store store,
                              Collection<PizzaConfiguration> pizzaConfigurations,
                              Row row ) throws SQLException, DataAccessException
    {
        order.setId( row.getLong( "orders.id" ) );
        processCustomer( order, row );
        order.setStore( store );
        order.setState( OrderState.fromInt( row.getInt( "orders.state" ) ) );
        order.setDelivering( row.getBoolean( "orders.delivering" ) );
        processAddress( order, row );
        order.setTimeAtStartOfDelivering( row.getTime( "orders.time_at_start_of_delivering" ) );
        order.setPizzaConfigurations( pizzaConfigurations );
    }

    private List<Object> createInsertArgs( Order order )
    {
        List<Object> args = new ArrayList<>();
        args.add( order.getCustomer().getId() );
        args.add( order.getStore().getId() );
        args.add( order.getState().toInt() );
        args.add( order.isDelivering() );
        if( order.getCustomerAddress() == null )
        {
            for( int i = 0; i < 5; i++ )
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

        return args;
    }

    private void processAddress( Order order, Row row ) throws SQLException
    {
        Address address = new Address();
        address.setStreet( row.getString( "orders.street" ) );
        address.setHouseNumber( row.getString( "orders.house_number" ) );
        address.setPostcode( row.getString( "orders.postcode" ) );
        address.setCity( row.getString( "orders.city" ) );
        address.setCountry( row.getString( "orders.country" ) );

        order.setCustomerAddress( address );
    }

    private void processCustomer( Order order, Row row ) throws SQLException, DataAccessException
    {
        long idCustomer = row.getLong( "orders.id_customer" );
        CustomerDatabaseDAO customerDAO = databaseDAOBundle.getCustomerDatabaseDAO();
        Customer customer = customerDAO.findCustomerById( idCustomer );
        if( customer == null )
            throw new DataAccessException( this, "Cannot find id_customer: " + idCustomer + "!" );

        order.setCustomer( customer );
    }
}
