package com.pizzaservice.value_objects;

/**
 * Created by philipp on 09.01.17.
 */
public class Ingredient
{
    private int id;
    private String name;
    private float pricePerGramm;

    public Ingredient()
    {
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
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

    public float getPricePerGramm()
    {
        return pricePerGramm;
    }

    public void setPricePerGramm( float pricePerGramm )
    {
        this.pricePerGramm = pricePerGramm;
    }
}
