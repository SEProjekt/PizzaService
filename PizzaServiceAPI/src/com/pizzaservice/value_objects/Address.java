package com.pizzaservice.value_objects;

/**
 * Created by philipp on 10.01.17.
 */
public class Address
{
    private String street;
    private String houseNumber;
    private String postcode;
    private String city;
    private String country;

    public Address()
    {
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    public String getHouseNumber()
    {
        return houseNumber;
    }

    public void setHouseNumber( String houseNumber )
    {
        this.houseNumber = houseNumber;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode( String postcode )
    {
        this.postcode = postcode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }
}
