package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.Topping;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface ToppingDAO
{
    void addTopping( Topping Topping ) throws DataAccessException;

    void deleteTopping( Topping Topping ) throws DataAccessException;

    void updateTopping( Topping Topping ) throws DataAccessException;

    Collection<Topping> getToppings() throws DataAccessException;
}
