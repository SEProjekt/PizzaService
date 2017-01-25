package com.pizzaservice.data_access_objects;

import java.sql.SQLException;

/**
 * Created by philipp on 11.01.17.
 */
public class DataAccessException extends Exception
{
    public DataAccessException()
    {
        super();
    }

    public DataAccessException( String message )
    {
        super( message );
    }

    public DataAccessException( Object producer, String message )
    {
        super( producer.getClass().getName() + ": " + message );
    }

    public DataAccessException( Object producer, SQLException e )
    {
        super( producer.getClass().getName() + ": SQL - " + e.getMessage() );
    }
}
