package com.pizzaservice.kitchenpage;

import com.pizzaservice.api.buissness_objects.Store;

/**
 * Created by philipp on 26.01.17.
 */
public class Session
{
    private Store store;

    public Session() {}

    public Store getStore()
    {
        return store;
    }

    public void setStore( Store store )
    {
        this.store = store;
    }
}
