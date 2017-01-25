package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.PizzaConfiguration;

/**
 * Created by philipp on 25.01.17.
 */
public interface PizzaConfigurationDAO
{
    void addPizzaConfiguration( PizzaConfiguration pizzaConfiguration ) throws DataAccessException;
}
