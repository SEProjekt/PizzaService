package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.buissness_objects.PizzaVariation;
import com.pizzaservice.buissness_objects.Topping;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.PizzaConfigurationDAO;
import com.pizzaservice.db.Database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by philipp on 25.01.17.
 */
public class PizzaConfigurationDatabaseDAO extends DatabaseDAO implements PizzaConfigurationDAO
{
    public PizzaConfigurationDatabaseDAO( Database database )
    {
        super( database );
    }

    @Override
    public void addPizzaConfiguration( PizzaConfiguration pizzaConfiguration ) throws DataAccessException
    {
        try
        {
            String query =
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

            database.query( query, args, row -> pizzaConfiguration.setId( row.getLong( 1 ) ) );
        }
        catch( Exception e ) { throw handleException( e ); }
    }
}
