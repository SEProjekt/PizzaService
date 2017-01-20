package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.buissness_objects.Recipe;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.PizzaVariationDAO;
import com.pizzaservice.buissness_objects.PizzaVariation;
import com.pizzaservice.data_access_objects.RecipeDAO;
import com.pizzaservice.db.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 10.01.17.
 */
public class PizzaVariationDatabaseDAO extends DatabaseDAO implements PizzaVariationDAO
{
    public static final int COLUMN_ID = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_ID_RECIPE_SMALL = 3;
    public static final int COLUMN_ID_RECIPE_LARGE = 4;
    public static final int COLUMN_ID_RECIPE_X_LARGE = 5;
    public static final int COLUMN_PRICE_SMALL = 6;
    public static final int COLUMN_PRICE_LARGE = 7;
    public static final int COLUMN_PRICE_X_LARGE = 8;

    public PizzaVariationDatabaseDAO( Database database )
    {
        super( database );
    }

    @Override
    public void addPizzaVariation( PizzaVariation pizzaVariation )
    {

    }

    @Override
    public void deletePizzaVariation( PizzaVariation pizzaVariation )
    {

    }

    @Override
    public void updatePizzaVariation( PizzaVariation pizzaVariation )
    {

    }

    @Override
    public Collection<PizzaVariation> getPizzaVariations() throws DataAccessException
    {
        try
        {
            Collection<PizzaVariation> pizzaVariations = new ArrayList<>();
            RecipeDAO recipeDAO = new RecipeDatabaseDAO( database );

            String query = "SELECT * FROM pizza_variations";
            database.query( query, new ArrayList<>(), row ->
            {
                long id = row.getLong( COLUMN_ID );
                String name = row.getString( COLUMN_NAME );
                long idRecipeSmall = row.getLong( COLUMN_ID_RECIPE_SMALL );
                long idRecipeLarge = row.getLong( COLUMN_ID_RECIPE_LARGE );
                long idRecipeXLarge = row.getLong( COLUMN_ID_RECIPE_X_LARGE );
                float priceSmall = row.getFloat( COLUMN_PRICE_SMALL );
                float priceLarge = row.getFloat( COLUMN_PRICE_LARGE );
                float priceXLarge = row.getFloat( COLUMN_PRICE_X_LARGE );

                Recipe recipeSmall = recipeDAO.findRecipeById( idRecipeSmall );
                if( recipeSmall == null )
                    throw new DataAccessException( this, "Cannot find recipe small with id: " + id + " !" );

                Recipe recipeLarge = recipeDAO.findRecipeById( idRecipeLarge );
                if( recipeLarge == null )
                    throw new DataAccessException( this, "Cannot find recipe large with id: " + id + " !" );

                Recipe recipeXLarge = recipeDAO.findRecipeById( idRecipeXLarge );
                if( recipeXLarge == null )
                    throw new DataAccessException( this, "Cannot find recipe x-large with id: " + id + " !" );

                PizzaVariation pizzaVariation = new PizzaVariation();
                pizzaVariation.setId( id );
                pizzaVariation.setName( name );
                pizzaVariation.setRecipeSmall( recipeSmall );
                pizzaVariation.setRecipeLarge( recipeLarge );
                pizzaVariation.setRecipeXLarge( recipeXLarge );
                pizzaVariation.setPriceSmall( priceSmall );
                pizzaVariation.setPriceLarge( priceLarge );
                pizzaVariation.setPriceXLarge( priceXLarge );

                pizzaVariations.add( pizzaVariation );
            } );

            return pizzaVariations;
        }
        catch( Exception e ) { throw handleException( e ); }
    }
}
