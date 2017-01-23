package com.pizzaservice.orderpage.view_fxml;

import java.net.URL;

/**
 * Created by philipp on 23.01.17.
 */
public class ViewURLs
{
    public static final ViewURLs instance = new ViewURLs();
    public static final URL TOPPING_SELECTOR = instance.getClass().getResource( "view_topping_selector.fxml" );
    public static final URL CART_ITEM = instance.getClass().getResource( "view_cart_item.fxml" );
}
