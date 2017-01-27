package com.pizzaservice.api.buissness_objects;

/**
 * Created by philipp on 10.01.17.
 */
public enum OrderState
{
    NEW( 0 ),
    COOKING( 1 ),
    DELIVERING( 2 ),
    FINISHED( 3 ),
    ABORTED( 4 );

    private int value;

    OrderState( int value )
    {
        this.value = value;
    }

    public int toInt()
    {
        return value;
    }

    public static OrderState fromInt( int value )
    {
        if( value == 0 ) return NEW;
        else if( value == 1 ) return COOKING;
        else if( value == 2 ) return DELIVERING;
        else if( value == 3 ) return FINISHED;
        else return ABORTED;
    }
}
