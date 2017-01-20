package com.pizzaservice.buissness_objects;

/**
 * Created by philipp on 10.01.17.
 */
public class Topping
{
    private long id;
    private String name;
    private float price;
    private Recipe recipe;

    public Topping()
    {
    }

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

    public float getPrice()
    {
        return price;
    }

    public void setPrice( float price )
    {
        this.price = price;
    }

    public Recipe getRecipe()
    {
        return recipe;
    }

    public void setRecipe( Recipe recipe )
    {
        this.recipe = recipe;
    }
}
