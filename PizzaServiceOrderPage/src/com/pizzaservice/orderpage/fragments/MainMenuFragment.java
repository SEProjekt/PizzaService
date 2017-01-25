package com.pizzaservice.orderpage.fragments;

import com.pizzaservice.db.Database;
import com.pizzaservice.orderpage.Session;
import com.pizzaservice.orderpage.Utils;
import com.pizzaservice.orderpage.fragment_fxml.FragmentURLs;
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
        super( FragmentURLs.MAIN_MENU, rootPane, session, database );
    }

    public MainMenuFragment( Fragment oldFragment )
    {
        super( FragmentURLs.MAIN_MENU, oldFragment );
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
