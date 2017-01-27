package com.pizzaservice.api.data_access_objects;

import com.pizzaservice.api.buissness_objects.PizzaConfiguration;
import com.pizzaservice.api.buissness_objects.Order;

/**
 * Created by philipp on 25.01.17.
 */
public interface PizzaConfigurationDAO
{
    void addPizzaConfiguration( PizzaConfiguration pizzaConfiguration ) throws DataAccessException;

    void getPizzaConfigurationsOfOrder( Order order ) throws DataAccessException;
}
