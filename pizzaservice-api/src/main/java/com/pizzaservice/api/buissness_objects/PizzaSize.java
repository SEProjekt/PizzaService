package com.pizzaservice.api.buissness_objects;

/**
 * Created by philipp on 10.01.17.
 */
public enum PizzaSize
{
    SMALL( 0 ),
    LARGE( 1 ),
    X_LARGE( 2 );

    private int value;

    PizzaSize( int value )
    {
        this.value = value;
    }

    public int toInt()
    {
        return value;
    }

    public static PizzaSize fromInt( int value )
    {
        if( value == 0 ) return SMALL;
        else if( value == 1 ) return LARGE;
        else return X_LARGE;
    }
}
