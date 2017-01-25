package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.Store;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface StoreDAO
{
    Collection<Store> getStores() throws DataAccessException;
}
