package com.pizzaservice.customerpage;

import com.pizzaservice.common.test.DummyFactory;
import com.pizzaservice.common.test.TestUtils;
import com.pizzaservice.customerpage.fragments.MainMenuFragment;
import com.pizzaservice.customerpage.fragments.ShowCartFragment;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by philipp on 27.01.17.
 */
public class CustomerPageTest extends GuiTest
{
    @Override
    protected Parent getRootNode()
    {
        Parent parent = null;

        try
        {
            parent = FXMLLoader.load( getClass().getResource( "main.fxml" ) );
        }
        catch( IOException e ) {}

        return parent;
    }

    /**
     * There are basically two methods on how to test a GUI.
     *
     * The first one is to simulate clicks. Example.
     * Example:
     * <code>
     *     click("#btnAddPizza");
     * </code>
     * Here we clicked on a GUI-Element with the id "btnAddPizza".
     * Those ids are given in fxml-files as "fx:id"-attributes so check out the fxml-files!
     */
    @Test
    public void clickAddPizzaTest()
    {
        // Clicks on the button with the "fx:id"-attribute "btnAddPizza" (see 'main_menu.fxml').
        click( "#btnAddPizza" );
        // Checks if the current fragment is 'ChoosePizzaSizeFragment'
        // by looking for the corresponding "fx:id"-attribute (see 'choose_pizza_size.fxml).
        find( "#fragChoosePizzaSize" );
    }

    /**
     * Here comes the second method of GUI testing which is to directly call
     * code from the UI-thread (this will be more complex tests).
     */
    @Test
    public void clickShowCartWithNonEmptyCartTest() throws InterruptedException
    {
        // We will test if we can display the 'ShowCartFragment' when we call the 'showCart()' method
        // from the 'MainMenuFragment'. This can only happen,
        // if we have at least one pizza configuration in our cart (more precisely: in the session).
        // Instead of simulating a click, we now call the method 'showCart()' directly from the current
        // 'MainMenuFragment' instance. Therefor, this has to be done on the UI-thread.
        MainMenuFragment mainMenu = find( "#fragMainMenu" );

        TestUtils.testOnUIThread( () ->
        {
            // Get the current session from the main menu (the abstraction of the cart,
            // where we can find the list of added pizza configurations).
            Session session = mainMenu.getSession();
            // Add a dummy pizza configuration to the session/cart.
            session.getPizzaConfigurations().add( DummyFactory.dummyPizzaConfiguration() );

            // Now we make the 'MainMenuFragment.showCart()' call which returns the instance
            // of the new 'ShowCartFragment' which is now being displayed on the UI.
            // If this wouldn't work, we get a ClassCastException and therefor failed the test.
            ShowCartFragment showCartFragment = (ShowCartFragment) mainMenu.showCart();

            // We cleanup the test by clearing the session and return to the main menu.
            // This is important because the other tests assume that we start off on the main menu.
            showCartFragment.finish().setSession( new Session() );
        } );
    }

    @Test
    public void mainMenuFragmentShowingTest()
    {
        find( "#fragMainMenu" );
    }

    @Test
    public void clickShowCartWithEmptyCartTest()
    {
        click( "#btnShowCart" );
        // As long as there aren't any pizza configurations added,
        // it should keep us on the main menu!
        find( "#fragMainMenu" );
    }

    @Test
    public void clickFinishOrderTest()
    {
        click( "#btnFinishOrder" );
        // As long as there aren't any pizza configurations added,
        // it should keep us on the main menu!
        find( "#fragMainMenu" );
    }
}
