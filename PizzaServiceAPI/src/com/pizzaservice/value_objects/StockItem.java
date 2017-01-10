package com.pizzaservice.value_objects;

/**
 * Created by philipp on 10.01.17.
 */
public class StockItem
{
    private Ingredient ingredient;
    private double currentQuantityInGrams;
    private double criticalQuantityInGrams;

    public StockItem()
    {
    }

    public Ingredient getIngredient()
    {
        return ingredient;
    }

    public void setIngredient( Ingredient ingredient )
    {
        this.ingredient = ingredient;
    }

    public double getCurrentQuantityInGrams()
    {
        return currentQuantityInGrams;
    }

    public void setCurrentQuantityInGrams( double currentQuantityInGrams )
    {
        this.currentQuantityInGrams = currentQuantityInGrams;
    }

    public double getCriticalQuantityInGrams()
    {
        return criticalQuantityInGrams;
    }

    public void setCriticalQuantityInGrams( double criticalQuantityInGrams )
    {
        this.criticalQuantityInGrams = criticalQuantityInGrams;
    }
}
