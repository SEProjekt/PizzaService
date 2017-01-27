package com.pizzaservice.customerpage.fragments;

import com.pizzaservice.api.db.Database;
import com.pizzaservice.customerpage.Session;
import com.pizzaservice.common.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by philipp on 23.01.17.
 */
public class MainMenuFragment extends Fragment
{
    @FXML public void actionAddPizza( ActionEvent actionEvent ) { addPizza(); }
    @FXML public void actionShowCart( ActionEvent actionEvent ) { showCart(); }
    @FXML public void actionFinishOrder( ActionEvent actionEvent ) { finishOrder(); }
    @FXML public void actionAbortOrder( ActionEvent actionEvent ) { abortOrder(); }

    public MainMenuFragment( Pane rootPane, Session session, Database database )
    {
        super( "main_menu.fxml", rootPane, session, database );
    }

    public MainMenuFragment( Fragment oldFragment )
    {
        super( "main_menu.fxml", oldFragment );
    }

    public Fragment addPizza()
    {
        return setNewFragment( new ChoosePizzaSizeFragment( this ) );
    }

    public Fragment showCart()
    {
        if( checkNonEmptyCard() )
            return setNewFragment( new ShowCartFragment( this ) );

        return null;
    }

    public Fragment finishOrder()
    {
        if( checkNonEmptyCard() )
            return setNewFragment( new FinishOrderFragment( this ) );

        return null;
    }

    public void abortOrder()
    {
        System.exit( 0 );
    }

    private boolean checkNonEmptyCard()
    {
        if( session.getPizzaConfigurations().isEmpty() )
        {
            Utils.showInputErrorMessage( "Es befindet sich keine Pizza im Warenkorb!" );
            return false;
        }

        return true;
    }
}
