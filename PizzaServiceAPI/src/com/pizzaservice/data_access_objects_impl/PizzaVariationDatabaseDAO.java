package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.buissness_objects.PizzaVariation;
import com.pizzaservice.buissness_objects.Recipe;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.PizzaVariationDAO;
import com.pizzaservice.data_access_objects.RecipeDAO;
import com.pizzaservice.db.Database;
import com.pizzaservice.db.Row;

import java.sql.SQLException;
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
    public PizzaVariation findPizzaVariationById( long id ) throws DataAccessException
    {
        try
        {
            PizzaVariation pizzaVariation = new PizzaVariation();

            String query = "SELECT * FROM pizza_variations WHERE id = ?";
            List<Object> args = new ArrayList<>();
            args.add( id );
            boolean found = database.query( query, args, row -> processPizzaVariation( pizzaVariation, row ) );
            if( !found )
                return null;

            return pizzaVariation;
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public Collection<PizzaVariation> getPizzaVariations() throws DataAccessException
    {
        try
        {
            Collection<PizzaVariation> pizzaVariations = new ArrayList<>();

            String query = "SELECT * FROM pizza_variations";
            database.query( query, new ArrayList<>(), row ->
            {
                PizzaVariation pizzaVariation = new PizzaVariation();
                processPizzaVariation( pizzaVariation, row );
                pizzaVariations.add( pizzaVariation );
            } );

            return pizzaVariations;
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    private void processPizzaVariation( PizzaVariation pizzaVariation, Row row ) throws SQLException, DataAccessException
    {
        pizzaVariation.setId( row.getLong( COLUMN_ID ) );
        pizzaVariation.setName( row.getString( COLUMN_NAME ) );
        processRecipes( pizzaVariation, row );
        pizzaVariation.setPriceSmall( row.getFloat( COLUMN_PRICE_SMALL ) );
        pizzaVariation.setPriceLarge( row.getFloat( COLUMN_PRICE_LARGE ) );
        pizzaVariation.setPriceXLarge( row.getFloat( COLUMN_PRICE_X_LARGE ) );
    }

    private void processRecipes( PizzaVariation pizzaVariation, Row row ) throws SQLException, DataAccessException
    {
        long idRecipeSmall = row.getLong( COLUMN_ID_RECIPE_SMALL );
        long idRecipeLarge = row.getLong( COLUMN_ID_RECIPE_LARGE );
        long idRecipeXLarge = row.getLong( COLUMN_ID_RECIPE_X_LARGE );

        RecipeDAO recipeDAO = new RecipeDatabaseDAO( database );

        Recipe recipeSmall = recipeDAO.findRecipeById( idRecipeSmall );
        if( recipeSmall == null )
            throw new DataAccessException( this, "Cannot find recipe small with id: " + idRecipeSmall + " !" );

        pizzaVariation.setRecipeSmall( recipeSmall );

        Recipe recipeLarge = recipeDAO.findRecipeById( idRecipeLarge );
        if( recipeLarge == null )
            throw new DataAccessException( this, "Cannot find recipe large with id: " + idRecipeLarge + " !" );

        pizzaVariation.setRecipeLarge( recipeLarge );

        Recipe recipeXLarge = recipeDAO.findRecipeById( idRecipeXLarge );
        if( recipeXLarge == null )
            throw new DataAccessException( this, "Cannot find recipe x-large with id: " + idRecipeXLarge + " !" );

        pizzaVariation.setRecipeXLarge( recipeXLarge );
    }
}
