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
    public MainMenuFragment( Pane rootPane, Session session, Database database )
    {
        super( "main_menu.fxml", rootPane, session, database );
    }

    public MainMenuFragment( Fragment oldFragment )
    {
        super( "main_menu.fxml", oldFragment );
    }

    @FXML
    public void actionAddPizza( ActionEvent actionEvent ) throws IOException
    {
        setNewFragment( new ChoosePizzaSizeFragment( this ) );
    }

    @FXML
    public void actionShowCart( ActionEvent actionEvent ) throws IOException
    {
        if( checkNonEmptyCard() )
            setNewFragment( new ShowCartFragment( this ) );
    }

    @FXML
    public void actionFinishOrder( ActionEvent actionEvent ) throws IOException
    {
        if( checkNonEmptyCard() )
            setNewFragment( new FinishOrderFragment( this ) );
    }

    @FXML
    public void actionAbortOrder( ActionEvent actionEvent )
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
