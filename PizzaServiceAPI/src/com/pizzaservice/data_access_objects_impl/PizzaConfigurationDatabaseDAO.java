package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.buissness_objects.*;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.PizzaConfigurationDAO;
import com.pizzaservice.data_access_objects.PizzaVariationDAO;
import com.pizzaservice.data_access_objects.ToppingDAO;
import com.pizzaservice.db.Database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by philipp on 25.01.17.
 */
public class PizzaConfigurationDatabaseDAO extends DatabaseDAO implements PizzaConfigurationDAO
{
    public static final int COLUMN_ID = 1;
    public static final int COLUMN_ID_ORDER = 2;
    public static final int COLUMN_SIZE = 3;
    public static final int COLUMN_SPLIT = 4;
    public static final int COLUMN_ID_PIZZA_VARIATION_1 = 5;
    public static final int COLUMN_ID_PIZZA_VARIATION_2 = 6;
    public static final int COLUMN_ID_TOPPING_1 = 7;
    public static final int COLUMN_ID_TOPPING_2 = 8;
    public static final int COLUMN_ID_TOPPING_3 = 9;
    public static final int COLUMN_ID_TOPPING_4 = 10;
    public static final int COLUMN_ID_TOPPING_5 = 11;

    public PizzaConfigurationDatabaseDAO( Database database )
    {
        super( database );
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

            database.update( insert, args, row -> pizzaConfiguration.setId( row.getLong( 1 ) ) );

            pizzaConfiguration.getOrder().getPizzaConfigurations().add( pizzaConfiguration );
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public void getPizzaConfigurationsOfOrder( Order order ) throws DataAccessException
    {
        try
        {
            Collection<PizzaConfiguration> pizzaConfigurations = new ArrayList<>();

            String query = "SELECT * FROM pizza_configurations WHERE id_order = ?";
            List<Object> args = new ArrayList<>();
            database.query( query, args, row ->
            {
                PizzaConfiguration pizzaConfiguration = new PizzaConfiguration();
                pizzaConfiguration.setOrder( order );
                pizzaConfiguration.setPizzaSize( PizzaSize.fromInt( row.getInt( COLUMN_SIZE ) ) );
                boolean split = row.getBoolean( COLUMN_SPLIT );
                pizzaConfiguration.setSplit( split );

                long idPizzaVariation1 = row.getLong( COLUMN_ID_PIZZA_VARIATION_1 );
                long idPizzaVariation2 = row.getLong( COLUMN_ID_PIZZA_VARIATION_1 );

                PizzaVariationDAO pizzaVariationDAO = new PizzaVariationDatabaseDAO( database );

                PizzaVariation pizzaVariation1 = pizzaVariationDAO.findPizzaVariationById( idPizzaVariation1 );
                if( pizzaVariation1 == null )
                    throw new DataAccessException( this, "Cannot find id_pizza_variation_1: " + idPizzaVariation1 + "!" );
                pizzaConfiguration.setPizzaVariation1( pizzaVariation1 );

                PizzaVariation pizzaVariation2 = pizzaVariationDAO.findPizzaVariationById( idPizzaVariation2 );
                if( split && pizzaVariation2 == null )
                    throw new DataAccessException( this, "Cannot find id_pizza_variation_2: " + idPizzaVariation1 + "!" );
                pizzaConfiguration.setPizzaVariation2( pizzaVariation2 );

                long idTopping1 = row.getLong( COLUMN_ID_TOPPING_1 );
                long idTopping2 = row.getLong( COLUMN_ID_TOPPING_2 );
                long idTopping3 = row.getLong( COLUMN_ID_TOPPING_3 );
                long idTopping4 = row.getLong( COLUMN_ID_TOPPING_4 );
                long idTopping5 = row.getLong( COLUMN_ID_TOPPING_5 );
                ToppingDAO toppingDAO = new ToppingDatabaseDAO( database );
                Collection<Topping> toppings = new ArrayList<>();
                toppings.add( toppingDAO.findToppingById( idTopping1 ) );
                toppings.add( toppingDAO.findToppingById( idTopping2 ) );
                toppings.add( toppingDAO.findToppingById( idTopping3 ) );
                toppings.add( toppingDAO.findToppingById( idTopping4 ) );
                toppings.add( toppingDAO.findToppingById( idTopping5 ) );
                pizzaConfiguration.setToppings( toppings );

                pizzaConfigurations.add( pizzaConfiguration );
            } );

            order.setPizzaConfigurations( pizzaConfigurations );
        }
        catch( Exception e ) { throw handleException( e ); }
    }
}
