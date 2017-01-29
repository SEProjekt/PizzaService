package com.pizzaservice.kitchenpage.fragments;

import com.pizzaservice.api.data_access_objects.DAOBundle;
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
    @FXML ChoiceBox<StoreItem> cbStore;

    @FXML public void actionShowOrders() { showOrders(); }

    public ChooseStoreFragment( Pane rootPane, Session session, DAOBundle daoBundle )
    {
        super( "choose_store.fxml", rootPane, session, daoBundle );
    }

    @Override
    public void setup()
    {
        try
        {
            StoreItem.setupChoiceBox( cbStore, daoBundle );
        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e );
        }
    }

    public Fragment showOrders()
    {
        StoreItem selectedStoreItem = cbStore.getValue();
        if( selectedStoreItem == null )
        {
            Utils.showInputErrorMessage( "Bitte wählen Sie eine Filiale!" );
            return this;
        }

        session.setStore( selectedStoreItem.getStore() );

        return setNewFragment( new ShowOrdersFragment( this ) );
    }
}
