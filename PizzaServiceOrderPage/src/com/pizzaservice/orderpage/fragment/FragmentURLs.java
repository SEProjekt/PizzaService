package com.pizzaservice.orderpage.fragment;

import java.net.URL;

/**
 * Created by philipp on 17.01.17.
 */
public class FragmentURLs
{
    public static final FragmentURLs instance = new FragmentURLs();

    public static final URL MAIN_MENU = instance.getClass().getResource( "fragment_main_menu.fxml" );
    public static final URL CHOOSE_PIZZA_SIZE = instance.getClass().getResource( "fragment_choose_pizza_size.fxml" );
    public static final URL CHOOSE_SPLIT = instance.getClass().getResource( "fragment_choose_split.fxml" );
    public static final URL CHOOSE_PIZZA_VARIATION = instance.getClass().getResource( "fragment_choose_pizza_variation.fxml" );
    public static final URL CHOOSE_TOPPINGS = instance.getClass().getResource( "fragment_choose_toppings" );
}
