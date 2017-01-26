package com.pizzaservice.kitchenpage.view_fxml;

import java.net.URL;

/**
 * Created by philipp on 26.01.17.
 */
public class ViewURLs
{
    public static final ViewURLs instance = new ViewURLs();
    public static final URL ORDER = instance.getClass().getResource( "view_order.fxml" );
}
