package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.Order;
import com.pizzaservice.buissness_objects.Store;

/**
 * Created by philipp on 24.01.17.
 */
public interface OrderDAO
{
    void addOrder( Order order ) throws DataAccessException;

    void getOrdersOfStore( Store store ) throws DataAccessException;
}
