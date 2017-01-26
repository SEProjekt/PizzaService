package com.pizzaservice.common.items;

import com.pizzaservice.buissness_objects.PizzaSize;
import com.pizzaservice.buissness_objects.PizzaVariation;

/**
 * Wrapper for PizzaVariation.
 */
public class PizzaVariationItem
{
    private PizzaVariation pizzaVariation;
    private PizzaSize pizzaSize;

    public PizzaVariationItem( PizzaVariation pizzaVariation, PizzaSize pizzaSize )
    {
        this.pizzaVariation = pizzaVariation;
        this.pizzaSize = pizzaSize;
    }

    public String toString()
    {
        return pizzaVariation.getName() + " " + String.format( "%.2f", getPrice() ) + "€";
    }

    public PizzaVariation getPizzaVariation()
    {
        return pizzaVariation;
    }

    public float getPrice()
    {
        if( pizzaSize == PizzaSize.SMALL )
            return pizzaVariation.getPriceSmall();
        else if( pizzaSize == PizzaSize.LARGE )
            return pizzaVariation.getPriceLarge();
        else
            return pizzaVariation.getPriceXLarge();
    }
}
