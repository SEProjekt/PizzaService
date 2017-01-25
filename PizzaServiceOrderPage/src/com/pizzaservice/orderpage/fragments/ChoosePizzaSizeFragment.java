package com.pizzaservice.orderpage.fragments;

import com.pizzaservice.orderpage.fragment_fxml.FragmentURLs;
import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.buissness_objects.PizzaSize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

/**
 * Created by philipp on 16.01.17.
 */
public class ChoosePizzaSizeFragment extends Fragment
{
    private ToggleGroup sizeGroup;

    @FXML
    RadioButton radSizeSmall;
    @FXML
    RadioButton radSizeLarge;
    @FXML
    RadioButton radSizeXLarge;

    public ChoosePizzaSizeFragment( Fragment oldFragment )
    {
        super( FragmentURLs.CHOOSE_PIZZA_SIZE, oldFragment );
    }

    @Override
    public void setup()
    {
        sizeGroup = new ToggleGroup();
        radSizeSmall.setToggleGroup( sizeGroup );
        radSizeLarge.setToggleGroup( sizeGroup );
        radSizeXLarge.setToggleGroup( sizeGroup );
        radSizeSmall.setSelected( true );
    }

    @FXML
    public void actionNext( ActionEvent actionEvent ) throws IOException
    {
        PizzaConfiguration pizzaConfiguration = new PizzaConfiguration();
        session.setCurrentPizzaConfiguration( pizzaConfiguration );

        if( radSizeSmall.isSelected() )
        {
            pizzaConfiguration.setPizzaSize( PizzaSize.SMALL );

            setNewFragment( new ChoosePizzaVariationFragment( this, false ) );
        }
        else
        {
            if( radSizeLarge.isSelected() )
                pizzaConfiguration.setPizzaSize( PizzaSize.LARGE );
            else
                pizzaConfiguration.setPizzaSize( PizzaSize.X_LARGE );

            setNewFragment( new ChooseSplitFragment( this ) );
        }
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        setNewFragment( new MainMenuFragment( this ) );
    }
}
