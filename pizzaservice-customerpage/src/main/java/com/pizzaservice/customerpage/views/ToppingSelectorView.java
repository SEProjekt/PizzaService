package com.pizzaservice.customerpage.views;

import com.pizzaservice.api.buissness_objects.Topping;
import com.pizzaservice.common.Utils;
import com.pizzaservice.common.items.ToppingItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Created by philipp on 23.01.17.
 */
/**
 * UI element to select a topping which contains a choice box of topping items and a delete button
 * to discard the topping selection.
 */
public class ToppingSelectorView extends GridPane
{
    public interface OnToppingItemChangedListener
    {
        void onToppingItemChanged( ToppingItem oldItem, ToppingItem newItem, ToppingSelectorView instance );
    }

    public interface OnDeleteListener
    {
        void onDelete( ToppingSelectorView instance );
    }

    @FXML ChoiceBox<ToppingItem> cbTopping;

    @FXML public void actionDelete() { delete(); }

    private OnToppingItemChangedListener onToppingItemChangedListener;
    private OnDeleteListener onDeleteListener;

    public ToppingSelectorView( List<ToppingItem> toppingItems )
    {
        super();

        Utils.load( getClass().getResource( "topping_selector.fxml" ), this );

        setupToppingChoiceBox( toppingItems );
    }

    public void delete()
    {
        if( onDeleteListener != null )
            onDeleteListener.onDelete( this );
    }

    public void select( int index )
    {
        cbTopping.getSelectionModel().select( index );
    }

    public ToppingItem getSelectedToppingItem()
    {
        return cbTopping.getValue();
    }

    public List<ToppingItem> getToppingItems()
    {
        return cbTopping.getItems();
    }

    public void setOnToppingItemChangedListener( OnToppingItemChangedListener onToppingItemChangedListener ) { this.onToppingItemChangedListener = onToppingItemChangedListener; }
    public void setOnDeleteListener( OnDeleteListener onDeleteListener )
    {
        this.onDeleteListener = onDeleteListener;
    }

    private void setupToppingChoiceBox( List<ToppingItem> toppingItems )
    {
        cbTopping.setItems( FXCollections.observableArrayList( toppingItems ) );
        cbTopping.getSelectionModel().selectedItemProperty().addListener( ( ov, oldItem, newItem ) ->
        {
            if( onToppingItemChangedListener != null )
                onToppingItemChangedListener.onToppingItemChanged( oldItem, newItem, this );
        } );
    }
}
