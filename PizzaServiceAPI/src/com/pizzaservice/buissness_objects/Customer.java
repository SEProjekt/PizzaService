package com.pizzaservice.buissness_objects;

import java.util.Date;

/**
 * Created by philipp on 10.01.17.
 */
public class Customer
{
    private long id;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private boolean locked;
    private Date lockedUnil;

    public Customer()
    {
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getSecondName()
    {
        return secondName;
    }

    public void setSecondName( String secondName )
    {
        this.secondName = secondName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber )
    {
        this.phoneNumber = phoneNumber;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public void setLocked( boolean locked )
    {
        this.locked = locked;
    }

    public Date getLockedUnil()
    {
        return lockedUnil;
    }

    public void setLockedUnil( Date lockedUnil )
    {
        this.lockedUnil = lockedUnil;
    }
}
