package com.pizzaservice.orderpage.controller;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.orderpage.fragment.FragmentURLs;
import com.pizzaservice.orderpage.items.PizzaConfigurationItem;
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
public class ShowCartController extends Controller
{
    private List<CartItem> cartItems;

    @FXML
    VBox vbContainer;

    @FXML
    Label lbTotalPrice;

    private class CartItem extends VBox
    {
        private GridPane vbHeader;
        private Label lbHeader;
        private Button btnExpand;
        private Button btnDelete;
        private Text txtConfigInfo;

        private PizzaConfiguration configuration;

        boolean expanded = false;

        public CartItem( PizzaConfiguration configuration )
        {
            this.configuration = configuration;

            setupHeader();
            setupConfigInfoLabel();
            getChildren().add( new Separator() );

            setSpacing( 10 );
        }

        private void setupHeader()
        {
            vbHeader = new GridPane();
            vbHeader.setHgap( 10 );

            setupLabel();
            setupExpandButton();
            setupDeleteButton();

            getChildren().add( vbHeader );
        }

        private void setupConfigInfoLabel()
        {
            PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( configuration );
            txtConfigInfo = new Text( configurationItem.toString() );
        }

        private void setupLabel()
        {
            String headerText = configuration.getPizzaVariation1().getName();
            if( configuration.isSplit() )
                headerText += " + " + configuration.getPizzaVariation2().getName();

            lbHeader = new Label( headerText );
            lbHeader.setWrapText( true );
            lbHeader.setStyle( "-fx-font-weight: bold" );

            vbHeader.getChildren().add( lbHeader );
            vbHeader.setColumnIndex( lbHeader, 0 );
            vbHeader.setHgrow( lbHeader, Priority.ALWAYS );
        }

        private void setupExpandButton()
        {
            btnExpand = new Button( "Info" );
            btnExpand.setOnAction( event ->
            {
                if( expanded )
                    CartItem.this.getChildren().remove( txtConfigInfo );
                else
                    CartItem.this.getChildren().add( 1, txtConfigInfo );

                expanded = !expanded;
            } );
            vbHeader.getChildren().add( btnExpand );
            vbHeader.setColumnIndex( btnExpand, 1 );
        }

        private void setupDeleteButton()
        {
            btnDelete = new Button( "Entfernen" );
            btnDelete.setOnAction( event ->
            {
                cartItems.remove( CartItem.this );
                vbContainer.getChildren().remove( CartItem.this );
                session.getOrder().getPizzaConfigurations().remove( configuration );
                updatePrice();
            } );
            vbHeader.getChildren().add( btnDelete );
            vbHeader.setColumnIndex( btnDelete, 2 );
        }
    }

    @Override
    protected void onSetup()
    {
        cartItems = new ArrayList<>();

        Collection<PizzaConfiguration> configurations = session.getOrder().getPizzaConfigurations();
        for( PizzaConfiguration configuration : configurations )
        {
            CartItem cartItem = new CartItem( configuration );
            cartItems.add( cartItem );
            vbContainer.getChildren().add( cartItem );
        }

        updatePrice();
    }

    @FXML
    public void actionFinished( ActionEvent actionEvent ) throws IOException
    {
        this.<MainMenuController>setFragment( FragmentURLs.MAIN_MENU );
    }

    private void updatePrice()
    {
        float totalPrice = 0;

        for( PizzaConfiguration configuration : session.getOrder().getPizzaConfigurations() )
        {
            PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( configuration );
            totalPrice += configurationItem.getTotalPrice();
        }

        lbTotalPrice.setText( "Gesamtpreis: " + String.format( "%.2f", totalPrice ) + "€" );
    }
}
