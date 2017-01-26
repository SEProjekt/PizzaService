package com.pizzaservice.customerpage.fragment_fxml;

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
    public static final URL CHOOSE_PIZZA_VARIATIONS = instance.getClass().getResource( "fragment_choose_pizza_variations.fxml" );
    public static final URL CHOOSE_TOPPINGS = instance.getClass().getResource( "fragment_choose_toppings.fxml" );
    public static final URL FINISH_PIZZA_CONFIGURATION = instance.getClass().getResource( "fragment_finish_pizza_configuration.fxml" );
    public static final URL SHOW_CART = instance.getClass().getResource( "fragment_show_card.fxml" );
    public static final URL FINISH_ORDER = instance.getClass().getResource( "fragment_finish_order.fxml" );
}
