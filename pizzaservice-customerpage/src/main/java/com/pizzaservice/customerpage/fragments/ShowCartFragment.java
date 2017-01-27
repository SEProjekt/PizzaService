package com.pizzaservice.customerpage.fragments;

import com.pizzaservice.api.buissness_objects.PizzaConfiguration;
import com.pizzaservice.common.items.PizzaConfigurationItem;
import com.pizzaservice.customerpage.views.CartItemView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 21.01.17.
 */
public class ShowCartFragment extends Fragment
{
    @FXML VBox vbContainer;
    @FXML Label lbTotalPrice;

    @FXML public void actionFinish( ActionEvent actionEvent ) { finish(); }

    private List<CartItemView> cartItemViews;

    public ShowCartFragment( Fragment oldFragment )
    {
        super( "show_cart.fxml", oldFragment );
    }

    @Override
    public void setup()
    {
        cartItemViews = new ArrayList<>();

        Collection<PizzaConfiguration> configurations = session.getPizzaConfigurations();
        for( PizzaConfiguration configuration : configurations )
        {
            CartItemView cartItemView = new CartItemView( configuration );
            cartItemView.setOnDeleteListener( instance ->
            {
                cartItemViews.remove( instance );
                vbContainer.getChildren().remove( instance );
                session.getPizzaConfigurations().remove( configuration );
                updatePrice();
            } );

            cartItemViews.add( cartItemView );
            vbContainer.getChildren().add( cartItemView );
        }

        updatePrice();
    }

    public Fragment finish()
    {
        return setNewFragment( new MainMenuFragment( this ) );
    }

    private void updatePrice()
    {
        float totalPrice = 0;

        for( PizzaConfiguration configuration : session.getPizzaConfigurations() )
        {
            PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( configuration );
            totalPrice += configurationItem.getTotalPrice();
        }

        lbTotalPrice.setText( "Gesamtpreis: " + String.format( "%.2f", totalPrice ) + "€" );
    }
}
