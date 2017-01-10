package com.pizzaservice.data_access_objects;

import com.pizzaservice.value_objects.Ingredient;

import java.util.Collection;

/**
 * Created by philipp on 09.01.17.
 */
public interface IngredientDAO
{
    void insertIngredient( Ingredient ingredient );

    void deleteIngredient( Ingredient ingredient );

    void updateIngredient( Ingredient ingredient );

    Collection<Ingredient> getIngredients();
}
