package com.pizzaservice.data_access_objects_impl;

import com.mysql.fabric.xmlrpc.base.Data;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.IngredientDAO;
import com.pizzaservice.buissness_objects.Ingredient;
import com.pizzaservice.db.Database;
import com.pizzaservice.db.OnRowProcessedListener;
import com.pizzaservice.db.Row;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 10.01.17.
 */
public class IngredientDatabaseDAO extends DatabaseDAO implements IngredientDAO
{
    public static final String TABLE_NAME = "ingredients";

    public static final int COLUMN_ID = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_PRICE_PER_GRAM = 3;

    public IngredientDatabaseDAO( Database database )
    {
        super( database );
    }

    @Override
    public void addIngredient( Ingredient ingredient ) throws DataAccessException
    {
        try
        {
            String query = "INSERT INTO " + TABLE_NAME + " (name, price_per_gram) VALUES (?, ?)";
            List<Object> args = new ArrayList<>();
            args.add( ingredient.getName() );
            args.add( ingredient.getPricePerGramm() );
            database.query( query, args, row -> ingredient.setId( row.getLong( 1 ) ) );
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public void deleteIngredient( Ingredient ingredient )
    {

    }

    @Override
    public void updateIngredient( Ingredient ingredient )
    {

    }

    @Override
    public Ingredient findIngredientById( long id ) throws DataAccessException
    {
        try
        {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
            List<Object> args = new ArrayList<>();
            args.add( id );
            Ingredient ingredient = new Ingredient();
            boolean found = database.query( query, args, row -> {
                ingredient.setName( row.getString( COLUMN_NAME ) );
                ingredient.setPricePerGramm( row.getFloat( COLUMN_PRICE_PER_GRAM ) );
            } );

            if( !found )
                return null;

            return ingredient;
        }
        catch( Exception e ) { throw handleException( e ); }
    }

    @Override
    public Collection<Ingredient> getIngredients()
    {
        return null;
    }
}
