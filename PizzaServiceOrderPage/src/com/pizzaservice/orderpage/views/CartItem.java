package com.pizzaservice.orderpage.views;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.items.PizzaConfigurationItem;
import com.pizzaservice.orderpage.view_fxml.ViewURLs;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by philipp on 23.01.17.
 */
public class CartItem extends VBox
{
    public interface OnDeleteListener
    {
        void onDelete( CartItem instance );
    }

    private PizzaConfiguration configuration;
    private boolean expanded = false;
    private OnDeleteListener onDeleteListener = null;

    @FXML
    Label lbHeader;

    @FXML
    Text txtInfo;

    public CartItem( PizzaConfiguration configuration )
    {
        this.configuration = configuration;

        Utils.load( ViewURLs.CART_ITEM, this );

        String headerText = configuration.getPizzaVariation1().getName();
        if( configuration.isSplit() )
            headerText += " + " + configuration.getPizzaVariation2().getName();
        lbHeader.setText( headerText );

        PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( configuration );
        txtInfo.setText( configurationItem.toString() );

        setExpanded( false );
    }

    public void setOnDeleteListener( OnDeleteListener onDeleteListener )
    {
        this.onDeleteListener = onDeleteListener;
    }

    @FXML
    public void actionToggleExpand()
    {
        setExpanded( !expanded );
    }

    public void setExpanded( boolean expanded )
    {
        txtInfo.setVisible( expanded );
        txtInfo.setManaged( expanded );

        this.expanded = expanded;
    }

    @FXML
    public void actionDelete()
    {
        if( onDeleteListener != null )
            onDeleteListener.onDelete( this );
    }
}