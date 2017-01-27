package com.pizzaservice.api.data_access_objects_impl;

import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.db.Database;

import java.sql.SQLException;

/**
 * Created by philipp on 19.01.17.
 */
public abstract class DatabaseDAO
{
    protected Database database;

    public DatabaseDAO( Database database )
    {
        this.database = database;
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
