package com.pizzaservice.orderpage;

import com.pizzaservice.buissness_objects.PizzaConfiguration;

/**
 * Created by philipp on 17.01.17.
 */
public class Session
{
    private PizzaConfiguration currentPizzaConfiguration;

    public PizzaConfiguration getCurrentPizzaConfiguration()
    {
        return currentPizzaConfiguration;
    }

    public void setCurrentPizzaConfiguration( PizzaConfiguration currentPizzaConfiguration )
    {
        this.currentPizzaConfiguration = currentPizzaConfiguration;
    }
}
