package com.pizzaservice.api.database_data_access_objects;

import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.db.Database;

import java.sql.SQLException;

/**
 * Created by philipp on 19.01.17.
 */
public abstract class DatabaseDAO
{
    protected Database database;
    protected DatabaseDAOBundle databaseDAOBundle;

    public DatabaseDAO( Database database, DatabaseDAOBundle databaseDAOBundle )
    {
        this.database = database;
        this.databaseDAOBundle = databaseDAOBundle;
    }

    /**
     * Little helper method, just to get rid of a billion of catch clauses.
     * @param e
     * @return
     */
    protected DataAccessException handleException( Exception e )
    {
        if( e instanceof SQLException )
            return new DataAccessException( this, (SQLException) e );
        else if( e instanceof DataAccessException )
            return (DataAccessException) e;
        else
            return new DataAccessException();
    }
}
