package com.pizzaservice.kitchenpage.fragments;

import com.pizzaservice.buissness_objects.Order;
import com.pizzaservice.buissness_objects.OrderState;
import com.pizzaservice.buissness_objects.Store;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.OrderDAO;
import com.pizzaservice.data_access_objects_impl.OrderDatabaseDAO;
import com.pizzaservice.kitchenpage.MyUtils;
import com.pizzaservice.kitchenpage.fragment_fxml.FragmentURLs;
import com.pizzaservice.kitchenpage.views.OrderView;
import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by philipp on 26.01.17.
 */
public class ShowOrdersFragment extends Fragment
{
    private List<OrderView> orderViews;

    @FXML
    VBox vbContainer;

    public ShowOrdersFragment( Fragment oldFragment )
    {
        super( FragmentURLs.SHOW_ORDERS, oldFragment );
    }

    public void setup()
    {
        updateOrders();
    }

    @FXML
    public void actionUpdateOrders()
    {
        updateOrders();
    }

    private void updateOrders()
    {
        vbContainer.getChildren().clear();
        orderViews = new ArrayList<>();

        try
        {
            Store store = session.getStore();

            OrderDAO orderDAO = new OrderDatabaseDAO( database );
            orderDAO.getOrdersOfStore( store );

            Collection<Order> orders = store.getOrders();
            for( Order order : orders )
            {
                // show only new or cooking orders
                if( order.getState() != OrderState.NEW && order.getState() != OrderState.COOKING )
                    continue;

                OrderView orderView = new OrderView( order );
                orderView.setOnOrderStateChangedListener( this::orderStateChanged );

                orderViews.add( orderView );
                vbContainer.getChildren().add( orderView );
            }
        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e );
        }
    }

    private void orderStateChanged( OrderView orderView )
    {
        Order order = orderView.getOrder();

        try
        {
            OrderDAO orderDAO = new OrderDatabaseDAO( database );
            orderDAO.updateOrder( order );
        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e );
        }

        OrderState state = order.getState();
        if( state == OrderState.FINISHED )
        {
            orderViews.remove( orderView );
            vbContainer.getChildren().remove( orderView );
        }
    }
}
