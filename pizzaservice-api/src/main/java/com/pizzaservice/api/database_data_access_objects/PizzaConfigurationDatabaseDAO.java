package com.pizzaservice.api.database_data_access_objects;

import com.pizzaservice.api.buissness_objects.*;
import com.pizzaservice.api.data_access_objects.*;
import com.pizzaservice.api.db.Row;
import com.pizzaservice.api.db.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by philipp on 25.01.17.
 */
public class PizzaConfigurationDatabaseDAO extends DatabaseDAO implements PizzaConfigurationDAO
{
    private static int queryCounter = 0;

    public PizzaConfigurationDatabaseDAO( Database database, DatabaseDAOBundle databaseDAOBundle )
    {
        super( database, databaseDAOBundle );
    }

    @Override
    public void addPizzaConfiguration( PizzaConfiguration pizzaConfiguration ) throws DataAccessException
    {
        try
        {
            String insert =
                "INSERT INTO pizza_configurations " +
                "(id_order, size, split, id_pizza_variation_1, id_pizza_variation_2, " +
                "id_topping_1, id_topping_2, id_topping_3, id_topping_4, id_topping_5) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            database.update(
                insert,
                createInsertArgs( pizzaConfiguration ),
                row -> pizzaConfiguration.setId( row.getLong( 1 ) )
            );

            pizzaConfiguration.getOrder().getPizzaConfigurations().add( pizzaConfiguration );
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public void getPizzaConfigurationsOfOrder( Order order ) throws DataAccessException
    {
        System.out.println( "PizzaConfiguration query number: " + ++queryCounter );

        try
        {
            Collection<PizzaConfiguration> pizzaConfigurations = new ArrayList<>();

            String query = "SELECT * FROM pizza_configurations WHERE id_order = ?";
            List<Object> args = new ArrayList<>();
            args.add( order.getId() );
            database.query( query, args, row ->
            {
                PizzaConfiguration pizzaConfiguration = new PizzaConfiguration();
                processPizzaConfiguration( pizzaConfiguration, order, row );
                pizzaConfigurations.add( pizzaConfiguration );
            } );

            order.setPizzaConfigurations( pizzaConfigurations );
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    public void processPizzaConfiguration( PizzaConfiguration pizzaConfiguration, Order order, Row row ) throws SQLException, DataAccessException
    {
        pizzaConfiguration.setId( row.getLong( "pizza_configurations.id" ) );
        pizzaConfiguration.setOrder( order );
        pizzaConfiguration.setPizzaSize( PizzaSize.fromInt( row.getInt( "pizza_configurations.size" ) ) );
        pizzaConfiguration.setSplit( row.getBoolean( "pizza_configurations.split" ) );
        processPizzaVariations( pizzaConfiguration, row );
        processToppings( pizzaConfiguration, row );
    }

    private List<Object> createInsertArgs( PizzaConfiguration pizzaConfiguration )
    {
        List<Object> args = new ArrayList<>();
        args.add( pizzaConfiguration.getOrder().getId() );
        args.add( pizzaConfiguration.getPizzaSize().toInt() );
        args.add( pizzaConfiguration.isSplit() );
        args.add( pizzaConfiguration.getPizzaVariation1().getId() );

        PizzaVariation pizzaVariation2 = pizzaConfiguration.getPizzaVariation2();
        if( pizzaVariation2 != null )
            args.add( pizzaVariation2.getId() );
        else
            args.add( null );

        Iterator it = pizzaConfiguration.getToppings().iterator();
        for( int i = 0; i < 5; i++ )
        {
            if( it.hasNext() )
            {
                Topping topping = ( Topping ) it.next();
                args.add( topping.getId() );
            }
            else args.add( null );
        }

        return args;
    }

    private void processToppings( PizzaConfiguration pizzaConfiguration, Row row ) throws SQLException, DataAccessException
    {
        long idTopping1 = row.getLong( "pizza_configurations.id_topping_1" );
        long idTopping2 = row.getLong( "pizza_configurations.id_topping_2" );
        long idTopping3 = row.getLong( "pizza_configurations.id_topping_3" );
        long idTopping4 = row.getLong( "pizza_configurations.id_topping_4" );
        long idTopping5 = row.getLong( "pizza_configurations.id_topping_5" );

        ToppingDatabaseDAO toppingDatabaseDAO = databaseDAOBundle.getToppingDatabaseDAO();

        Collection<Topping> toppings = new ArrayList<>();
        Topping topping1 = toppingDatabaseDAO.findToppingById( idTopping1 );
        Topping topping2 = toppingDatabaseDAO.findToppingById( idTopping2 );
        Topping topping3 = toppingDatabaseDAO.findToppingById( idTopping3 );
        Topping topping4 = toppingDatabaseDAO.findToppingById( idTopping4 );
        Topping topping5 = toppingDatabaseDAO.findToppingById( idTopping5 );

        if( topping1 != null ) toppings.add( topping1 );
        if( topping2 != null ) toppings.add( topping2 );
        if( topping3 != null ) toppings.add( topping3 );
        if( topping4 != null ) toppings.add( topping4 );
        if( topping5 != null ) toppings.add( topping5 );

        pizzaConfiguration.setToppings( toppings );
    }

    private void processPizzaVariations( PizzaConfiguration pizzaConfiguration, Row row ) throws SQLException, DataAccessException
    {
        long idPizzaVariation1 = row.getLong( "pizza_configurations.id_pizza_variation_1" );
        long idPizzaVariation2 = row.getLong( "pizza_configurations.id_pizza_variation_2" );
        boolean split = row.getBoolean( "pizza_configurations.split" );

        PizzaVariationDatabaseDAO pizzaVariationDatabaseDAO = databaseDAOBundle.getPizzaVariationDatabaseDAO();

        PizzaVariation pizzaVariation1 = pizzaVariationDatabaseDAO.findPizzaVariationById( idPizzaVariation1 );
        if( pizzaVariation1 == null )
            throw new DataAccessException( this, "Cannot find id_pizza_variation_1: " + idPizzaVariation1 + "!" );

        pizzaConfiguration.setPizzaVariation1( pizzaVariation1 );

        PizzaVariation pizzaVariation2 = pizzaVariationDatabaseDAO.findPizzaVariationById( idPizzaVariation2 );
        if( split && pizzaVariation2 == null )
            throw new DataAccessException( this, "Cannot find id_pizza_variation_2: " + idPizzaVariation2 + "!" );

        if( pizzaVariation2 != null )
            pizzaConfiguration.setPizzaVariation2( pizzaVariation2 );
    }
}
