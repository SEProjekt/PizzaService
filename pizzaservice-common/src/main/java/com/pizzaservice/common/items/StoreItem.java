package com.pizzaservice.common.items;

import com.pizzaservice.api.buissness_objects.Address;
import com.pizzaservice.api.buissness_objects.Store;
import com.pizzaservice.api.data_access_objects.DataAccessException;
import com.pizzaservice.api.data_access_objects.StoreDAO;
import com.pizzaservice.api.data_access_objects_impl.StoreDatabaseDAO;
import com.pizzaservice.api.db.Database;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by philipp on 25.01.17.
 */
public class StoreItem
{
    private Store store;

    public StoreItem( Store store )
    {
        this.store = store;
    }

    public Store getStore()
    {
        return store;
    }

    @Override
    public String toString()
    {
        Address address = store.getAddress();
        return address.getStreet() + " "
            + address.getHouseNumber() + " "
            + address.getPostcode() + " "
            + address.getCity() + " "
            + address.getCountry();
    }

    public static void setupChoiceBox( ChoiceBox<StoreItem> choiceBox, Database database ) throws DataAccessException
    {
        StoreDAO storeDAO = new StoreDatabaseDAO( database );
        Collection<Store> stores = storeDAO.getStores();

        Collection<StoreItem> storeItems = new ArrayList<>();
        for( Store store : stores )
        {
            StoreItem storeItem = new StoreItem( store );
            storeItems.add( storeItem );
        }

        choiceBox.setItems( FXCollections.observableArrayList( storeItems ) );
    }
}
