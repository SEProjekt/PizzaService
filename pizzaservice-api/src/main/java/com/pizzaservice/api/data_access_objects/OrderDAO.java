package com.pizzaservice.api.data_access_objects;

import com.pizzaservice.api.buissness_objects.Order;
import com.pizzaservice.api.buissness_objects.Store;

/**
 * Created by philipp on 24.01.17.
 */
public interface OrderDAO
{
    void addOrder( Order order ) throws DataAccessException;

    void updateOrder( Order order ) throws DataAccessException;

    void getOrdersOfStore( Store store ) throws DataAccessException;
}
