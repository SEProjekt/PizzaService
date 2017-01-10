package com.pizzaservice.value_objects;

import java.util.Collection;

/**
 * Created by philipp on 10.01.17.
 */
public class Store
{
    private int id;
    private Address address;
    private Collection<StockItem> stockItems;
    private Collection<Order> order;

    private Store()
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

    public Collection<Order> getOrder()
    {
        return order;
    }

    public void setOrder( Collection<Order> order )
    {
        this.order = order;
    }
}
