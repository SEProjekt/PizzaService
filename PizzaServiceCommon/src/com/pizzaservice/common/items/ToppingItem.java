package com.pizzaservice.common.items;

import com.pizzaservice.buissness_objects.Topping;

/**
 * A wrapper class of Topping which implements the toString() method which gets the text to be displayed
 * in the choice boxes of the topping selectors.
 */
public class ToppingItem
{
    private Topping topping;

    public ToppingItem( Topping topping )
    {
        this.topping = topping;
    }

    public Topping getTopping()
    {
        return topping;
    }

    @Override
    public String toString()
    {
        return topping.getName() + " " + String.format( "%.2f", topping.getPrice() ) + "€";
    }
}
