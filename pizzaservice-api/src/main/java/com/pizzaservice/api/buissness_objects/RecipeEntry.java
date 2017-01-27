package com.pizzaservice.api.buissness_objects;

/**
 * Created by philipp on 10.01.17.
 */
public class RecipeEntry
{
    private Recipe recipe;
    private Ingredient ingredient;
    private float quantityInGrams;

    public RecipeEntry() {}

    public Recipe getRecipe()
    {
        return recipe;
    }

    public void setRecipe( Recipe recipe )
    {
        this.recipe = recipe;
    }

    public Ingredient getIngredient()
    {
        return ingredient;
    }

    public void setIngredient( Ingredient ingredient )
    {
        this.ingredient = ingredient;
    }

    public float getQuantityInGrams()
    {
        return quantityInGrams;
    }

    public void setQuantityInGrams( float quantityInGrams )
    {
        this.quantityInGrams = quantityInGrams;
    }
}
