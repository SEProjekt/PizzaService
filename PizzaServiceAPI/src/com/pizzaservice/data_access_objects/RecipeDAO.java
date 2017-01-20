package com.pizzaservice.data_access_objects;

import com.pizzaservice.buissness_objects.Recipe;

/**
 * Created by philipp on 19.01.17.
 */
public interface RecipeDAO
{
    Recipe findRecipeById( long id ) throws DataAccessException;
}
