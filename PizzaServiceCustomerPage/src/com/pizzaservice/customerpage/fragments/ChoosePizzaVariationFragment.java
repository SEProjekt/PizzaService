package com.pizzaservice.customerpage.fragments;

import com.pizzaservice.buissness_objects.PizzaConfiguration;
import com.pizzaservice.buissness_objects.PizzaVariation;
import com.pizzaservice.customerpage.MyUtils;
import com.pizzaservice.data_access_objects.DataAccessException;
import com.pizzaservice.data_access_objects.PizzaVariationDAO;
import com.pizzaservice.data_access_objects_impl.PizzaVariationDatabaseDAO;
import com.pizzaservice.common.Utils;
import com.pizzaservice.customerpage.fragment_fxml.FragmentURLs;
import com.pizzaservice.common.items.PizzaVariationItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by philipp on 17.01.17.
 */
public class ChoosePizzaVariationFragment extends Fragment
{
    @FXML
    ChoiceBox<PizzaVariationItem> cbPizzaVariation1;

    @FXML
    ChoiceBox<PizzaVariationItem> cbPizzaVariation2;

    public ChoosePizzaVariationFragment( Fragment oldFragment, boolean split )
    {
        super( split ? FragmentURLs.CHOOSE_PIZZA_VARIATIONS : FragmentURLs.CHOOSE_PIZZA_VARIATION, oldFragment );
    }

    @Override
    public void setup()
    {
        try
        {
            // get the pizza variations and create a list of items to display name and price in the choice box
            Collection<PizzaVariationItem> variationItems = getPizzaVariationItems();
            cbPizzaVariation1.setItems( FXCollections.observableArrayList( variationItems ) );

            // if split - set items for second choice box
            if( session.getCurrentPizzaConfiguration().isSplit() )
                cbPizzaVariation2.setItems( FXCollections.observableArrayList( variationItems ) );

        }
        catch( DataAccessException e )
        {
            MyUtils.handleDataAccessException( e, this );
        }
    }

    @FXML
    public void actionAbort( ActionEvent actionEvent ) throws IOException
    {
        setNewFragment( new MainMenuFragment( this ) );
    }

    @FXML
    public void actionNext( ActionEvent actionEvent ) throws IOException
    {
        PizzaVariationItem selectedItem1 = cbPizzaVariation1.getValue();
        if( selectedItem1 == null )
        {
            Utils.showInputErrorMessage( "Es wurde noch keine Variation ausgewählt!" );
            return;
        }

        PizzaConfiguration currentConfiguration = session.getCurrentPizzaConfiguration();
        currentConfiguration.setPizzaVariation1( selectedItem1.getPizzaVariation() );

        if( currentConfiguration.isSplit() )
        {
            PizzaVariationItem selectedItem2 = cbPizzaVariation2.getValue();
            if( selectedItem2 == null )
            {
                Utils.showInputErrorMessage( "Es wurde noch keine Variation ausgewählt!" );
                return;
            }

            currentConfiguration.setPizzaVariation2( selectedItem2.getPizzaVariation() );
        }

        setNewFragment( new ChooseToppingsFragment( this ) );
    }

    private Collection<PizzaVariationItem> getPizzaVariationItems() throws DataAccessException
    {
        PizzaVariationDAO pizzaVariationDAO = new PizzaVariationDatabaseDAO( database );
        Collection<PizzaVariation> variations = pizzaVariationDAO.getPizzaVariations();
        Collection<PizzaVariationItem> variationItems = new ArrayList<>();
        for( PizzaVariation variation : variations )
        {
            PizzaVariationItem variationItem = new PizzaVariationItem(
                variation, session.getCurrentPizzaConfiguration().getPizzaSize() );
            variationItems.add( variationItem );
        }

        return variationItems;
    }
}
