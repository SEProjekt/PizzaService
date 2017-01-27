package com.pizzaservice.api.buissness_objects;

import java.util.Collection;

/**
 * Created by philipp on 09.01.17.
 */
public class Recipe
{
    private long id;

    private Collection<RecipeEntry> entries;

    public Recipe() {}

    public long getId()
    {
        return id;
    }

    public void setId( long id )
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
