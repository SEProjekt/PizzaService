package com.pizzaservice.customerpage;

import com.pizzaservice.api.buissness_objects.PizzaConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 17.01.17.
 */
public class Session
{
    private PizzaConfiguration currentPizzaConfiguration;
    private List<PizzaConfiguration> pizzaConfigurations;

    public Session()
    {
        pizzaConfigurations = new ArrayList<>();
    }

    public PizzaConfiguration getCurrentPizzaConfiguration()
    {
        return currentPizzaConfiguration;
    }

    public void setCurrentPizzaConfiguration( PizzaConfiguration currentPizzaConfiguration )
    {
        this.currentPizzaConfiguration = currentPizzaConfiguration;
    }

    public List<PizzaConfiguration> getPizzaConfigurations()
    {
        return pizzaConfigurations;
    }
}
