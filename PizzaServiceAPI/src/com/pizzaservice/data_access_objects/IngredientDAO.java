package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.Ingredient;

import java.util.Collection;

/**
 * Created by philipp on 09.01.17.
 */
public interface IngredientDAO
{
    Ingredient findIngredientById( long id ) throws DataAccessException;
}
