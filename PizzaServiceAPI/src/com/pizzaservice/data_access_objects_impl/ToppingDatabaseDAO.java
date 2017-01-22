package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.buissness_objects.Recipe;
import com.pizzaservice.buissness_objects.Topping;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.RecipeDAO;
import com.pizzaservice.data_access_objects.ToppingDAO;
import com.pizzaservice.db.Database;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public class ToppingDatabaseDAO extends DatabaseDAO implements ToppingDAO
{
    public static final int COLUMN_ID = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_PRICE = 3;
    public static final int COLUMN_ID_RECIPE = 4;

    public ToppingDatabaseDAO( Database database ) { super( database ); }

    @Override
    public void addTopping( Topping Topping )
    {

    }

    @Override
    public void deleteTopping( Topping Topping )
    {

    }

    @Override
    public void updateTopping( Topping Topping )
    {

    }

    @Override
    public Collection<Topping> getToppings() throws DataAccessException
    {
        try
        {
            Collection<Topping> toppings = new ArrayList<>();
            RecipeDAO recipeDAO = new RecipeDatabaseDAO( database );

            String query = "SELECT * FROM toppings";
            database.query( query, new ArrayList<>(), row ->
            {
                Topping topping = new Topping();
                topping.setId( row.getLong( COLUMN_ID ) );
                topping.setName( row.getString( COLUMN_NAME ) );
                topping.setPrice( row.getFloat( COLUMN_PRICE ) );
                Recipe recipe = recipeDAO.findRecipeById( row.getLong( COLUMN_ID_RECIPE ) );
                topping.setRecipe( recipe );

                toppings.add( topping );
            } );

            return toppings;
        }
        catch( Exception e ) { throw handleException( e ); }
    }
}
