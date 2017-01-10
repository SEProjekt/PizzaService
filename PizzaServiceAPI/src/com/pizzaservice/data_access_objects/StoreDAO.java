package com.pizzaservice.data_access_objects;

import com.pizzaservice.value_objects.Store;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface StoreDAO
{
    void insertStore( Store Store );

    void deleteStore( Store Store );

    void updateStore( Store Store );

    void updateStockItems( Store store );

    void updateOrders( Store store );

    Collection<Store> getStores();
}
