package com.pizzaservice.data_access_objects;

import com.pizzaservice.value_objects.PizzaVariation;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface PizzaVariationDAO
{
    void insertPizzaVariation( PizzaVariation pizzaVariation );

    void deletePizzaVariation( PizzaVariation pizzaVariation );

    void updatePizzaVariation( PizzaVariation pizzaVariation );

    Collection<PizzaVariation> getPizzaVariations( PizzaVariation pizzaVariation );
}
