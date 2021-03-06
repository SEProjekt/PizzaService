package com.pizzaservice.api.buissness_objects;

/**
 * Created by philipp on 10.01.17.
 */
public class PizzaVariation
{
    private long id;
    private String name;
    private Recipe recipeSmall;
    private Recipe recipeLarge;
    private Recipe recipeXLarge;
    private float priceSmall;
    private float priceLarge;
    private float priceXLarge;

    public PizzaVariation() {}

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Recipe getRecipeSmall()
    {
        return recipeSmall;
    }

    public void setRecipeSmall( Recipe recipeSmall )
    {
        this.recipeSmall = recipeSmall;
    }

    public Recipe getRecipeLarge()
    {
        return recipeLarge;
    }

    public void setRecipeLarge( Recipe recipeLarge )
    {
        this.recipeLarge = recipeLarge;
    }

    public Recipe getRecipeXLarge()
    {
        return recipeXLarge;
    }

    public void setRecipeXLarge( Recipe recipeXLarge )
    {
        this.recipeXLarge = recipeXLarge;
    }

    public float getPriceSmall()
    {
        return priceSmall;
    }

    public void setPriceSmall( float priceSmall )
    {
        this.priceSmall = priceSmall;
    }

    public float getPriceLarge()
    {
        return priceLarge;
    }

    public void setPriceLarge( float priceLarge )
    {
        this.priceLarge = priceLarge;
    }

    public float getPriceXLarge()
    {
        return priceXLarge;
    }

    public void setPriceXLarge( float priceXLarge )
    {
        this.priceXLarge = priceXLarge;
    }
}
