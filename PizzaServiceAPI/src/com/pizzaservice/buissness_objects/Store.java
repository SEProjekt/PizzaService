package com.pizzaservice.buissness_objects;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public class Store
{
    private long id;
    private Address address;

    private Collection<StockItem> stockItems;
    private Collection<Order> orders;

    public Store() {}

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress( Address address )
    {
        this.address = address;
    }

    public Collection<StockItem> getStockItems()
    {
        return stockItems;
    }

    public void setStockItems( Collection<StockItem> stockItems )
    {
        this.stockItems = stockItems;
    }

    public Collection<Order> getOrders()
    {
        return orders;
    }

    public void setOrders( Collection<Order> orders )
    {
        this.orders = orders;
    }
}
