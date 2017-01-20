package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.Ingredient;

import java.util.Collection;

/**
 * Created by philipp on 09.01.17.
 */
public interface IngredientDAO
{
    void addIngredient( Ingredient ingredient ) throws DataAccessException;

    void deleteIngredient( Ingredient ingredient ) throws DataAccessException;

    void updateIngredient( Ingredient ingredient ) throws DataAccessException;

    Ingredient findIngredientById( long id ) throws DataAccessException;

    Collection<Ingredient> getIngredients() throws DataAccessException;
}
