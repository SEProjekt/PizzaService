package com.pizzaservice.orderpage.items;

import com.pizzaservice.buissness_objects.Address;
import com.pizzaservice.buissness_objects.Store;

/**
 * Created by philipp on 25.01.17.
 */
public class StoreItem
{
    private Store store;

    public StoreItem( Store store )
    {
        this.store = store;
    }

    public Store getStore()
    {
        return store;
    }

    @Override
    public String toString()
    {
        Address address = store.getAddress();
        return address.getStreet() + " "
            + address.getHouseNumber() + " "
            + address.getPostcode() + " "
            + address.getCity() + " "
            + address.getCountry();
    }
}
