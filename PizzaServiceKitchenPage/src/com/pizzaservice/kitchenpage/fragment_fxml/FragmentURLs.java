package com.pizzaservice.kitchenpage.fragment_fxml;

import java.net.URL;

/**
 * Created by philipp on 26.01.17.
 */
public class FragmentURLs
{
    public static final FragmentURLs instance = new FragmentURLs();
    public static final URL CHOOSE_STORE = instance.getClass().getResource( "fragment_choose_store.fxml" );
    public static final URL SHOW_ORDERS = instance.getClass().getResource( "fragment_show_orders.fxml" );
}
