package com.pizzaservice.orderpage.fragments;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.orderpage.fragment_fxml.FragmentURLs;
import com.pizzaservice.orderpage.items.PizzaConfigurationItem;
import com.pizzaservice.orderpage.views.CartItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 21.01.17.
 */
public class ShowCartFragment extends Fragment
{
    private List<CartItem> cartItems;

    @FXML
    VBox vbContainer;

    @FXML
    Label lbTotalPrice;

    public ShowCartFragment( Fragment oldFragment )
    {
        super( FragmentURLs.SHOW_CART, oldFragment );
    }

    @Override
    public void setup()
    {
        cartItems = new ArrayList<>();

        Collection<PizzaConfiguration> configurations = session.getPizzaConfigurations();
        for( PizzaConfiguration configuration : configurations )
        {
            CartItem cartItem = new CartItem( configuration );
            cartItem.setOnDeleteListener( instance ->
            {
                cartItems.remove( instance );
                vbContainer.getChildren().remove( instance );
                session.getPizzaConfigurations().remove( configuration );
                updatePrice();
            } );

            cartItems.add( cartItem );
            vbContainer.getChildren().add( cartItem );
        }

        updatePrice();
    }

    @FXML
    public void actionFinished( ActionEvent actionEvent ) throws IOException
    {
        setNewFragment( new MainMenuFragment( this ) );
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
