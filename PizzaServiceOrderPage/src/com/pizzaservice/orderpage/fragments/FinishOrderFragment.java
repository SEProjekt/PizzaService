package com.pizzaservice.orderpage.fragments;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.orderpage.fragment_fxml.FragmentURLs;
import com.pizzaservice.orderpage.items.PizzaConfigurationItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by philipp on 22.01.17.
 */
public class FinishOrderFragment extends Fragment
{
    @FXML
    TextField tfFirstName, tfSecondName, tfPhoneNumber;

    @FXML
    ChoiceBox cbStore;

    @FXML
    Text txtBill;

    private boolean billShowing;

    public FinishOrderFragment( Fragment oldFragment )
    {
        super( FragmentURLs.FINISH_ORDER, oldFragment );
    }

    @Override
    protected void onLoadFinished()
    {
        setupBill();
    }

    @FXML
    public void actionShowBill( ActionEvent actionEvent )
    {
        showBill( !billShowing );
    }

    @FXML
    public void actionSubmitOrder( ActionEvent actionEvent )
    {

    }

    @FXML
    public void actionAbort( ActionEvent actionEvent )
    {
        setNewFragment( new MainMenuFragment( this ) );
    }

    private void setupBill()
    {
        String bill = "";
        for( PizzaConfiguration configuration : session.getOrder().getPizzaConfigurations() )
        {
            PizzaConfigurationItem configurationItem = new PizzaConfigurationItem( configuration );
            bill += configurationItem.toString() + "\n\n##############\n\n";
        }

        txtBill.setText( bill );
        showBill( false );
    }

    private void showBill( boolean show )
    {
        txtBill.setVisible( show );
        txtBill.setManaged( show );

        billShowing = show;
    }
}
