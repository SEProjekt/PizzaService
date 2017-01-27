package com.pizzaservice.api.data_access_objects;

import com.pizzaservice.api.buissness_objects.Topping;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface ToppingDAO
{
    Topping findToppingById( long id ) throws DataAccessException;

    Collection<Topping> getToppings() throws DataAccessException;
}
