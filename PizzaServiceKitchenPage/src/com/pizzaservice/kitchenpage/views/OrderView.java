package com.pizzaservice.kitchenpage.views;

import com.pizzaservice.buissness_objects.*;
import com.pizzaservice.common.Utils;
import com.pizzaservice.kitchenpage.view_fxml.ViewURLs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by philipp on 26.01.17.
 */
public class OrderView extends VBox
{
    public interface OnOrderStateChangedListener
    {
        void onOrderStateChanged( OrderView instance );
    }

    private Order order;
    private OnOrderStateChangedListener onOrderStateChangedListener = null;

    @FXML
    Label lbOrderId;

    @FXML
    Button btnChangeState;

    @FXML
    Text txtOrderContent;

    public OrderView( Order order )
    {
        super();

        this.order = order;

        Utils.load( ViewURLs.ORDER, this );

        lbOrderId.setText( "Bestellungs-ID: " + order.getId() );
        updateChangeStateButton();
        setupOrderContentText();
    }

    @FXML
    public void actionChangeState()
    {
        OrderState state = order.getState();
        if( state == OrderState.NEW )
            order.setState( OrderState.COOKING );
        else if( state == OrderState.COOKING )
            order.setState( OrderState.FINISHED );
        else return;

        updateChangeStateButton();

        if( onOrderStateChangedListener != null )
            onOrderStateChangedListener.onOrderStateChanged( this );
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOnOrderStateChangedListener( OnOrderStateChangedListener onOrderStateChangedListener )
    {
        this.onOrderStateChangedListener = onOrderStateChangedListener;
    }

    private void updateChangeStateButton()
    {
        OrderState state = order.getState();
        if( state == OrderState.NEW )
            btnChangeState.setText( "Status: NEU" );
        else if( state == OrderState.COOKING )
            btnChangeState.setText( "Status: BACKEND" );
        else
            btnChangeState.setText( "Status: FERTIG" );
    }

    private void setupOrderContentText()
    {
        String text = "";

        for( PizzaConfiguration configuration : order.getPizzaConfigurations() )
        {
            text += "Größe: ";
            PizzaSize size = configuration.getPizzaSize();
            if( size == PizzaSize.SMALL )
                text += "SMALL";
            else if( size == PizzaSize.LARGE )
                text += "LARGE";
            else
                text += "X-LARGE";

            PizzaVariation variation1 = configuration.getPizzaVariation1();
            PizzaVariation variation2 = configuration.getPizzaVariation2();

            text += "\nVariation(en): " + variation1.getName();

            if( variation2 != null )
                text += " + " + variation2.getName();

            text += " \nToppings:";
            for( Topping topping : configuration.getToppings() )
                text += " + " + topping.getName();

            text += "\n\n";
        }

        txtOrderContent.setText( text );
    }
}
