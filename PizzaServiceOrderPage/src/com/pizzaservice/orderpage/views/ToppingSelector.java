package com.pizzaservice.orderpage.views;

import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.items.ToppingItem;
import com.pizzaservice.orderpage.view_fxml.ViewURLs;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * Created by philipp on 23.01.17.
 */
/**
 * UI element to select a topping which contains a choice box of topping items and a delete button
 * to discard the topping selection.
 */
public class ToppingSelector extends GridPane
{
    public interface OnToppingItemChangedListener
    {
        void onToppingItemChanged( ToppingItem oldItem, ToppingItem newItem, ToppingSelector instance );
    }

    public interface OnDeleteListener
    {
        void onDelete( ToppingSelector instance );
    }

    private OnToppingItemChangedListener onToppingItemChangedListener;
    private OnDeleteListener onDeleteListener;

    @FXML
    ChoiceBox cbTopping;

    public ToppingSelector( List<ToppingItem> toppingItems )
    {
        super();

        Utils.load( ViewURLs.TOPPING_SELECTOR, this );

        setupToppingChoiceBox( toppingItems );
    }

    public void setOnToppingItemChangedListener( OnToppingItemChangedListener onToppingItemChangedListener )
    {
        this.onToppingItemChangedListener = onToppingItemChangedListener;
    }

    public void setOnDeleteListener( OnDeleteListener onDeleteListener )
    {
        this.onDeleteListener = onDeleteListener;
    }

    private void setupToppingChoiceBox( List<ToppingItem> toppingItems )
    {
        cbTopping.setItems( FXCollections.observableArrayList( toppingItems ) );
        cbTopping.getSelectionModel().selectedIndexProperty().addListener( ( ov, oldIndex, newIndex ) ->
        {
            // Very important check: when we remove an item from the item list, we might have
            // selected the item with the biggest index before. Therefore, this index will be updated
            // and that means the oldIndex becomes out of bounds!
            if( oldIndex.intValue() >= getToppingItems().size() )
                return;

            // must check if unselected
            ToppingItem oldItem = null;
            if( oldIndex.intValue() >= 0 )
                oldItem = getToppingItems().get( oldIndex.intValue() );

            ToppingItem newItem = getToppingItems().get( newIndex.intValue() );

            if( onToppingItemChangedListener != null )
                onToppingItemChangedListener.onToppingItemChanged( oldItem, newItem, this );
        } );
    }

    @FXML
    public void actionDelete()
    {
        if( onDeleteListener != null )
            onDeleteListener.onDelete( this );
    }

    public ToppingItem getSelectedToppingItem()
    {
        return (ToppingItem) cbTopping.getSelectionModel().getSelectedItem();
    }

    public List<ToppingItem> getToppingItems()
    {
        return cbTopping.getItems();
    }
}
