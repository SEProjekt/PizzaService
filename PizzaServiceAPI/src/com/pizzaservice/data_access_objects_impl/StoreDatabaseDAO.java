package com.pizzaservice.data_access_objects_impl;

import com.pizzaservice.data_access_objects.StoreDAO;
import com.pizzaservice.buissness_objects.Store;
import com.pizzaservice.db.Database;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public class StoreDatabaseDAO extends DatabaseDAO implements StoreDAO
{
    public StoreDatabaseDAO( Database database )
    {
        super( database );
    }

    @Override
    public void addStore( Store Store )
    {

    }

    @Override
    public void deleteStore( Store Store )
    {

    }

    @Override
    public void updateStore( Store Store )
    {

    }

    @Override
    public Collection<Store> getStores()
    {
        return null;
    }
}
