package com.pizzaservice.customerpage.fragments;

import com.pizzaservice.api.buissness_objects.PizzaConfiguration;
import com.pizzaservice.api.buissness_objects.PizzaSize;
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
    @FXML RadioButton radSizeSmall;
    @FXML RadioButton radSizeLarge;
    @FXML RadioButton radSizeXLarge;

    @FXML public void actionNext( ActionEvent actionEvent ) { next(); }
    @FXML public void actionAbort( ActionEvent actionEvent ) { abort(); }

    public ChoosePizzaSizeFragment( Fragment oldFragment )
    {
        super( "choose_pizza_size.fxml", oldFragment );
    }

    @Override
    public void setup()
    {
        ToggleGroup sizeGroup = new ToggleGroup();
        radSizeSmall.setToggleGroup( sizeGroup );
        radSizeLarge.setToggleGroup( sizeGroup );
        radSizeXLarge.setToggleGroup( sizeGroup );
        radSizeSmall.setSelected( true );
    }

    public ChoosePizzaSizeFragment choose( PizzaSize pizzaSize )
    {
        if( pizzaSize == PizzaSize.SMALL )
            radSizeSmall.setSelected( true );
        else if( pizzaSize == PizzaSize.LARGE )
            radSizeLarge.setSelected( true );
        else
            radSizeXLarge.setSelected( true );

        return this;
    }

    public Fragment next()
    {
        PizzaConfiguration pizzaConfiguration = new PizzaConfiguration();
        session.setCurrentPizzaConfiguration( pizzaConfiguration );

        if( radSizeSmall.isSelected() )
        {
            pizzaConfiguration.setPizzaSize( PizzaSize.SMALL );

            return setNewFragment( new ChoosePizzaVariationFragment( this, false ) );
        }
        else
        {
            if( radSizeLarge.isSelected() )
                pizzaConfiguration.setPizzaSize( PizzaSize.LARGE );
            else
                pizzaConfiguration.setPizzaSize( PizzaSize.X_LARGE );

            return setNewFragment( new ChooseSplitFragment( this ) );
        }
    }

    public Fragment abort()
    {
        return setNewFragment( new MainMenuFragment( this ) );
    }
}
