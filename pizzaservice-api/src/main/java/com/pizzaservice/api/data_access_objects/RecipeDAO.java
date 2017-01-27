package com.pizzaservice.api.data_access_objects;

import com.pizzaservice.api.buissness_objects.Recipe;

/**
 * Created by philipp on 19.01.17.
 */
public interface RecipeDAO
{
    Recipe findRecipeById( long id ) throws DataAccessException;
}
