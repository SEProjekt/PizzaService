package com.pizzaservice.kitchenpage;

import com.pizzaservice.common.Utils;
import com.pizzaservice.api.data_access_objects.DataAccessException;

/**
 * Created by philipp on 26.01.17.
 */
public class MyUtils
{
    /**
     * Simply shows error dialog.
     * @param e
     */
    public static void handleDataAccessException( DataAccessException e )
    {
        Utils.showConnectionErrorMessage( e.getMessage() );
    }
}
