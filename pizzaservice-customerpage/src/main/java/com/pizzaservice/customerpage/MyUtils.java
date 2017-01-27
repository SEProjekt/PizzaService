package com.pizzaservice.customerpage;

import com.pizzaservice.common.Utils;
import com.pizzaservice.customerpage.fragments.Fragment;
import com.pizzaservice.customerpage.fragments.MainMenuFragment;
import com.pizzaservice.api.data_access_objects.DataAccessException;

/**
 * Created by philipp on 26.01.17.
 */
public class MyUtils
{
    /**
     * Shows error dialog and returns to main menu.
     * @param e
     * @param currentFragment
     */
    public static void handleDataAccessException( DataAccessException e, Fragment currentFragment )
    {
        Utils.showConnectionErrorMessage( e.getMessage() );
        currentFragment.setNewFragment( new MainMenuFragment( currentFragment ) );
    }
}
