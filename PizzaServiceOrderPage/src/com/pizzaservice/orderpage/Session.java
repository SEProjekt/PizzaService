package com.pizzaservice.orderpage;

import com.pizzaservice.buissness_objects.Order;
import com.pizzaservice.buissness_objects.PizzaConfiguration;

import java.util.ArrayList;

/**
 * Created by philipp on 17.01.17.
 */
public class Session
{
    private PizzaConfiguration currentPizzaConfiguration;
    private Order order;

    public Session()
    {
        order = new Order();
        order.setPizzaConfigurations( new ArrayList<>() );
    }

    public PizzaConfiguration getCurrentPizzaConfiguration()
    {
        return currentPizzaConfiguration;
    }

    public void setCurrentPizzaConfiguration( PizzaConfiguration currentPizzaConfiguration )
    {
        this.currentPizzaConfiguration = currentPizzaConfiguration;
    }

    public Order getOrder()
    {
        return order;
    }
}
