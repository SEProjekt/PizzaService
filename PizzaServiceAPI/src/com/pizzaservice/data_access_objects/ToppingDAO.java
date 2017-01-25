package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.Topping;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface ToppingDAO
{
    Topping findToppingById( long id ) throws DataAccessException;

    Collection<Topping> getToppings() throws DataAccessException;
}
