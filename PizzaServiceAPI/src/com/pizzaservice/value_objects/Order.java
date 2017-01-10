package com.pizzaservice.value_objects;

import java.util.Collection;
import java.util.Date;

/**
 * Created by philipp on 10.01.17.
 */
public class Order
{
    private int id;
    private Customer customer;
    private Store store;
    private OrderState state;
    private Collection<PizzaConfiguration> pizzaConfigurations;
    private boolean delivering;
    private Address customerAddress;
    private Date timeAtStartOfDelivering;

    private Order()
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

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer( Customer customer )
    {
        this.customer = customer;
    }

    public Store getStore()
    {
        return store;
    }

    public void setStore( Store store )
    {
        this.store = store;
    }

    public OrderState getState()
    {
        return state;
    }

    public void setState( OrderState state )
    {
        this.state = state;
    }

    public Collection<PizzaConfiguration> getPizzaConfigurations()
    {
        return pizzaConfigurations;
    }

    public void setPizzaConfigurations( Collection<PizzaConfiguration> pizzaConfigurations )
    {
        this.pizzaConfigurations = pizzaConfigurations;
    }

    public boolean isDelivering()
    {
        return delivering;
    }

    public void setDelivering( boolean delivering )
    {
        this.delivering = delivering;
    }

    public Address getCustomerAddress()
    {
        return customerAddress;
    }

    public void setCustomerAddress( Address customerAddress )
    {
        this.customerAddress = customerAddress;
    }

    public Date getTimeAtStartOfDelivering()
    {
        return timeAtStartOfDelivering;
    }

    public void setTimeAtStartOfDelivering( Date timeAtStartOfDelivering )
    {
        this.timeAtStartOfDelivering = timeAtStartOfDelivering;
    }
}
