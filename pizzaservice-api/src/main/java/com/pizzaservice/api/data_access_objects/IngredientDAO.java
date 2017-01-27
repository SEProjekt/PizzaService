package com.pizzaservice.api.data_access_objects;

import com.pizzaservice.api.buissness_objects.Ingredient;

/**
 * Created by philipp on 09.01.17.
 */
public interface IngredientDAO
{
    Ingredient findIngredientById( long id ) throws DataAccessException;
}
