package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.PizzaVariation;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface PizzaVariationDAO
{
    PizzaVariation findPizzaVariationById( long id ) throws DataAccessException;

    Collection<PizzaVariation> getPizzaVariations() throws DataAccessException;
}
