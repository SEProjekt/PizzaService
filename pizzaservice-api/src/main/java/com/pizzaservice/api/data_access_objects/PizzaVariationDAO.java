package com.pizzaservice.api.data_access_objects;

import com.pizzaservice.api.buissness_objects.PizzaVariation;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface PizzaVariationDAO
{
    PizzaVariation findPizzaVariationById( long id ) throws DataAccessException;

    Collection<PizzaVariation> getPizzaVariations() throws DataAccessException;
}
