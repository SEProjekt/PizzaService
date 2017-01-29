package com.pizzaservice.api.data_access_objects_impl;

import com.pizzaservice.api.data_access_objects.DAOBundle;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.db.Database;

import java.sql.SQLException;

/**
 * Created by philipp on 19.01.17.
 */
public abstract class DatabaseDAO
{
    protected Database database;
    protected DAOBundle daoBundle;

    public DatabaseDAO( Database database, DAOBundle daoBundle )
    {
        this.database = database;
        this.daoBundle = daoBundle;
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
