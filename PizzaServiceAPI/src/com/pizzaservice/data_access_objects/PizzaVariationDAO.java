package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.PizzaVariation;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface PizzaVariationDAO
{
    void addPizzaVariation( PizzaVariation pizzaVariation ) throws DataAccessException;

    void deletePizzaVariation( PizzaVariation pizzaVariation ) throws DataAccessException;

    void updatePizzaVariation( PizzaVariation pizzaVariation ) throws DataAccessException;

    Collection<PizzaVariation> getPizzaVariations() throws DataAccessException;
}
