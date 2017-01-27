package com.pizzaservice.customerpage.fragments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

/**
 * Created by philipp on 17.01.17.
 */
public class ChooseSplitFragment extends Fragment
{
    @FXML RadioButton radSplitYes;

    @FXML RadioButton radSplitNo;

    @FXML public void actionNext( ActionEvent actionEvent ) { next(); }
    @FXML public void actionAbort( ActionEvent actionEvent ) { abort(); }

    public ChooseSplitFragment( Fragment oldFragment )
    {
        super( "choose_split.fxml", oldFragment );
    }

    @Override
    public void setup()
    {
        ToggleGroup splitGroup = new ToggleGroup();
        radSplitYes.setToggleGroup( splitGroup );
        radSplitNo.setToggleGroup( splitGroup );
        radSplitNo.setSelected( true );
    }

    public ChooseSplitFragment choose( boolean split )
    {
        if( split )
            radSplitYes.setSelected( true );
        else
            radSplitNo.setSelected( true );

        return this;
    }

    public Fragment next()
    {
        if( radSplitYes.isSelected() )
        {
            session.getCurrentPizzaConfiguration().setSplit( true );
            return setNewFragment( new ChoosePizzaVariationFragment( this, true ) );
        }
        else
        {
            session.getCurrentPizzaConfiguration().setSplit( false );
            return setNewFragment( new ChoosePizzaVariationFragment( this, false ) );
        }
    }

    public Fragment abort()
    {
        return setNewFragment( new MainMenuFragment( this ) );
    }
}
