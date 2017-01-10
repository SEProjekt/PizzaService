package com.pizzaservice.value_objects;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public class PizzaConfiguration
{
    private int id;
    private PizzaSize size;
    private boolean split;
    private PizzaVariation pizzaVariation1;
    private PizzaVariation pizzaVariation2;
    private Collection<Topping> toppings;

    private PizzaConfiguration()
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

    public PizzaSize getSize()
    {
        return size;
    }

    public void setSize( PizzaSize size )
    {
        this.size = size;
    }

    public boolean isSplit()
    {
        return split;
    }

    public void setSplit( boolean split )
    {
        this.split = split;
    }

    public PizzaVariation getPizzaVariation1()
    {
        return pizzaVariation1;
    }

    public void setPizzaVariation1( PizzaVariation pizzaVariation1 )
    {
        this.pizzaVariation1 = pizzaVariation1;
    }

    public PizzaVariation getPizzaVariation2()
    {
        return pizzaVariation2;
    }

    public void setPizzaVariation2( PizzaVariation pizzaVariation2 )
    {
        this.pizzaVariation2 = pizzaVariation2;
    }

    public Collection<Topping> getToppings()
    {
        return toppings;
    }

    public void setToppings( Collection<Topping> toppings )
    {
        this.toppings = toppings;
    }
}
