package com.pizzaservice.kitchenpage.views;

import com.pizzaservice.api.buissness_objects.*;
import com.pizzaservice.common.Utils;
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

    @FXML Label lbOrderId;
    @FXML Button btnChangeState;
    @FXML Text txtOrderContent;

    @FXML public void actionChangeState() { changeState(); }

    private Order order;
    private OnOrderStateChangedListener onOrderStateChangedListener = null;

    public OrderView( Order order )
    {
        super();

        this.order = order;

        Utils.load( getClass().getResource( "order.fxml" ), this );

        lbOrderId.setText( "Bestellungs-ID: " + order.getId() );
        updateChangeStateButton();
        setupOrderContentText();
    }

    public OrderView changeState()
    {
        OrderState state = order.getState();
        if( state == OrderState.NEW )
            order.setState( OrderState.COOKING );
        else if( state == OrderState.COOKING )
            order.setState( OrderState.FINISHED );
        else return this;

        updateChangeStateButton();

        if( onOrderStateChangedListener != null )
            onOrderStateChangedListener.onOrderStateChanged( this );

        return this;
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
