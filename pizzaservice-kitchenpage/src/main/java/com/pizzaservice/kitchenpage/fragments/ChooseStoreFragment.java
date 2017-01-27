package com.pizzaservice.kitchenpage.fragments;

import com.pizzaservice.common.Utils;
import com.pizzaservice.common.items.StoreItem;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.db.Database;
import com.pizzaservice.kitchenpage.MyUtils;
import com.pizzaservice.kitchenpage.Session;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

/**
 * Created by philipp on 26.01.17.
 */
public class ChooseStoreFragment extends Fragment
{
    @FXML
    ChoiceBox<StoreItem> cbStore;

    public ChooseStoreFragment( Pane rootPane, Session session, Database database )
    {
        super( "choose_store.fxml", rootPane, session, database );
    }

    @Override
    public void setup()
    {
        try
        {
            StoreItem.setupChoiceBox( cbStore, database );
        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e );
        }
    }

    @FXML
    public void actionShowOrders()
    {
        StoreItem selectedStoreItem = cbStore.getValue();
        if( selectedStoreItem == null )
        {
            Utils.showInputErrorMessage( "Bitte wählen Sie eine Filiale!" );
            return;
        }

        session.setStore( selectedStoreItem.getStore() );

        setNewFragment( new ShowOrdersFragment( this ) );
    }
}