package com.pizzaservice.data_access_objects;

import com.pizzaservice.value_objects.Topping;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public interface ToppingDAO
{
    void insertTopping( Topping Topping );

    void deleteTopping( Topping Topping );

    void updateTopping( Topping Topping );

    Collection<Topping> getToppings( Topping Topping );
}
