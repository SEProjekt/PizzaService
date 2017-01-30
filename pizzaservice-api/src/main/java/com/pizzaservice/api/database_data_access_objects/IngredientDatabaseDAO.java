package com.pizzaservice.api.database_data_access_objects;

import com.pizzaservice.api.buissness_objects.Ingredient;
import com.pizzaservice.api.data_access_objects.DAOBundle;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.data_access_objects.IngredientDAO;
import com.pizzaservice.api.db.Database;

import java.util.ArrayList;
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

    public IngredientDatabaseDAO( Database database, DatabaseDAOBundle databaseDAOBundle )
    {
        super( database, databaseDAOBundle );
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
            boolean found = database.query( query, args, row ->
            {
                ingredient.setName( row.getString( COLUMN_NAME ) );
                ingredient.setPricePerGramm( row.getFloat( COLUMN_PRICE_PER_GRAM ) );
            } );

            if( !found )
                return null;

            return ingredient;
        }
        catch( Exception e ) { throw handleException( e ); }
    }
}
