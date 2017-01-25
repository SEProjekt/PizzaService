package com.pizzaservice.buissness_objects;

import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 10.01.17.
 */
public class PizzaConfiguration
{
    private long id;
    private Order order;
    private PizzaSize pizzaSize;
    private boolean split;
    private PizzaVariation pizzaVariation1;
    private PizzaVariation pizzaVariation2;
    private Collection<Topping> toppings;

    public PizzaConfiguration() {}

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder( Order order )
    {
        this.order = order;
    }

    public PizzaSize getPizzaSize()
    {
        return pizzaSize;
    }

    public void setPizzaSize( PizzaSize pizzaSize )
    {
        this.pizzaSize = pizzaSize;
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
