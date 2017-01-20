package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.db.Database;

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
}
