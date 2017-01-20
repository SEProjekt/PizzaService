package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.Store;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface StoreDAO
{
    void addStore( Store Store ) throws DataAccessException;

    void deleteStore( Store Store ) throws DataAccessException;

    void updateStore( Store Store ) throws DataAccessException;

    void updateStockItems( Store store ) throws DataAccessException;

    void updateOrders( Store store ) throws DataAccessException;

    Collection<Store> getStores() throws DataAccessException;
}
