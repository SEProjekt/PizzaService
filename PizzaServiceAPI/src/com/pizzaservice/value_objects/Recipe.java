package com.pizzaservice.value_objects;

import java.util.Collection;

/**
 * Created by philipp on 09.01.17.
 */
public class Recipe
{
    private int id;
    private Collection<RecipeEntry> entries;

    public Recipe()
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

    public Collection<RecipeEntry> getEntries()
    {
        return entries;
    }

    public void setEntries( Collection<RecipeEntry> entries )
    {
        this.entries = entries;
    }
}
