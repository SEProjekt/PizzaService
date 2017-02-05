package com.pizzaservice.customerpage;

import com.pizzaservice.api.buissness_objects.PizzaSize;
import com.pizzaservice.common.test.DummyFactory;
import com.pizzaservice.common.test.TestUtils;
import com.pizzaservice.customerpage.fragments.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.loadui.testfx.GuiTest;

import java.awt.event.KeyListener;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by philipp on 27.01.17.
 */
public class CustomerPageTest extends GuiTest {
    @Override
    protected Parent getRootNode() {
        Parent parent = null;

        try {
            parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        } catch (IOException e) {
        }

        return parent;
    }

    /**
     * There are basically two methods on how to test a GUI.
     * <p>
     * The first one is to simulate clicks. Example.
     * Example:
     * <code>
     * click("#btnAddPizza");
     * </code>
     * Here we clicked on a GUI-Element with the id "btnAddPizza".
     * Those ids are given in fxml-files as "fx:id"-attributes so check out the fxml-files!
     */


    @Test
    public void clickAddPizzaTest() {
        // Clicks on the button with the "fx:id"-attribute "btnAddPizza" (see 'main_menu.fxml').
        click("#btnAddPizza");
        // Checks if the current fragment is 'ChoosePizzaSizeFragment'
        // by looking for the corresponding "fx:id"-attribute (see 'choose_pizza_size.fxml).
        find("#fragChoosePizzaSize");
        click("#btnAbort");//return to main menu
    }

    @Ignore("Functionality was already tested in showEmptyCartTest() without clicking.")
    @Test
    public void clickShowCartWithEmptyCartTest() {
        click("#btnShowCart");
        // As long as there aren't any pizza configurations added,
        // it should keep us on the main menu!
        find("#fragMainMenu");
    }


    @Test
    public void clickFinishOrderTest() {
        click("#btnFinishOrder");
        // As long as there aren't any pizza configurations added,
        // it should keep us on the main menu!
        find("#fragMainMenu");
    }


    /**
     * Here comes the second method of GUI testing which is to directly call
     * code from the UI-thread (this will be more complex tests).
     */
    @Test
    public void showNonEmptyCartTest() throws InterruptedException {
        // We will test if we can display the 'ShowCartFragment' when we call the 'showCart()' method
        // from the 'MainMenuFragment'. This can only happen,
        // if we have at least one pizza configuration in our cart (more precisely: in the session).
        // Instead of simulating a click, we now call the method 'showCart()' directly from the current
        // 'MainMenuFragment' instance. Therefore, this has to be done on the UI-thread.
        MainMenuFragment mainMenu = find("#fragMainMenu");

        TestUtils.testOnUIThread(() ->
        {
            // Get the current session from the main menu (the abstraction of the cart,
            // where we can find the list of added pizza configurations).
            Session session = mainMenu.getSession();
            // Add a dummy pizza configuration to the session/cart.
            session.getPizzaConfigurations().add(DummyFactory.dummyPizzaConfiguration());

            // Now we make the 'MainMenuFragment.showCart()' call which returns the instance
            // of the new 'ShowCartFragment' which is now being displayed on the UI.
            // If this wouldn't work, we get a ClassCastException and therefore failed the test.
            ShowCartFragment showCartFragment = (ShowCartFragment) mainMenu.showCart();

            // We cleanup the test by clearing the session and return to the main menu.
            // This is important because the other tests assume that we start off on the main menu.
            showCartFragment.finish().setSession(new Session());

        });

    }

    @Test
    public void showEmptyCartTest() {
        TestUtils.testOnUIThread(() ->
        {
            MainMenuFragment mainMenu = find("#fragMainMenu");
            mainMenu.showCart();
            //This should cause a warning popup, because we have no orders in the cart. thus, we remain on the mainMenu Fragment
            closePopups();
            find("#fragMainMenu");
        });
    }


    public void closePopups() {
        //press ESCAPE to get rid of Popup Windows
        push(KeyCode.ESCAPE);
    }


    @Test
    public void mainMenuFragmentShowingTest() {
        //test whether the main menu is there at the beginning
        find("#fragMainMenu");
    }


    //this test should find the "ChoosePizzaVariation" Fragment after choosing a size. If L/XL, it also finds the "ChooseSplit" Fragment
    public void testPizzaSize(PizzaSize size) {
        TestUtils.testOnUIThread(() ->
        {
            MainMenuFragment mainMenu = find("#fragMainMenu");

            ChoosePizzaSizeFragment choosePizzaSizeFragment = (ChoosePizzaSizeFragment) mainMenu.addPizza();
            find("#fragChoosePizzaSize");
            choosePizzaSizeFragment.choose(size);

            ChoosePizzaVariationFragment choosePizzaVariationFragment;
            if (size == PizzaSize.SMALL) {
                choosePizzaVariationFragment = (ChoosePizzaVariationFragment) choosePizzaSizeFragment.next();
                find("#fragChoosePizzaVariation");
            } else {
                ChooseSplitFragment chooseSplitFragment = (ChooseSplitFragment) choosePizzaSizeFragment.next();
                find("#fragChooseSplit");
                choosePizzaVariationFragment = (ChoosePizzaVariationFragment) chooseSplitFragment.next();
                find("#fragChoosePizzaVariation");
            }
            //go back to main menu
            choosePizzaVariationFragment.abort();
            find("#fragMainMenu");
        });
    }

    //Test arguments for testPizzaSize
    @Test
    public void pizzaSizeSmallTest() {
        testPizzaSize(PizzaSize.SMALL);
    }

    @Test
    public void pizzaSizeLargeTest() {
        testPizzaSize(PizzaSize.LARGE);
    }

    @Test
    public void pizzaSizeXLargeTest() {
        testPizzaSize(PizzaSize.X_LARGE);
    }


    //choosing not to split a LARGE/XLARGE Pizza
    public void testChooseNOSplitPizzaVariation(PizzaSize size) {
        TestUtils.testOnUIThread(() ->
        {
            MainMenuFragment mainMenu = find("#fragMainMenu");

            ChoosePizzaSizeFragment choosePizzaSizeFragment = (ChoosePizzaSizeFragment) mainMenu.addPizza();
            find("#fragChoosePizzaSize");
            choosePizzaSizeFragment.choose(size);

            ChooseSplitFragment chooseSplitFragment = (ChooseSplitFragment) choosePizzaSizeFragment.next();
            chooseSplitFragment.choose(false);

            ChoosePizzaVariationFragment choosePizzaVariationFragment = (ChoosePizzaVariationFragment) chooseSplitFragment.next();
            find("#fragChoosePizzaVariation");
            choosePizzaVariationFragment.choose(0);//Margherita
            choosePizzaVariationFragment.next();
            find("#fragChooseToppings");
        });
    }

    //test arguments for testChooseNOSplitPizzaVariation for LARGE and XLARGE
    @Test
    public void chooseNOSplitPizzaVariationLARGETest() {
        testChooseNOSplitPizzaVariation(PizzaSize.LARGE);
    }

    @Test
    public void chooseNOSplitPizzaVariationXLARGETest() {
        testChooseNOSplitPizzaVariation(PizzaSize.X_LARGE);
    }


    //basically the same as above, but without choosing Split
    public void testChooseSplitPizzaVariations(PizzaSize size) {

    }

    //test arguments for testChooseSplitPizzaVariation for LARGE and XLARGE
    @Test
    public void chooseSplitPizzaVariationLARGETest() {
        testChooseSplitPizzaVariations(PizzaSize.LARGE);
    }
    @Test
    public void chooseSplitPizzaVariationXLARGETest() {
        testChooseSplitPizzaVariations(PizzaSize.X_LARGE);
    }


    @Test
    public void chooseNoVariationTest() {   //If no variation is chosen, a warning should popup and the fragment remain as is.
        TestUtils.testOnUIThread(() ->
        {
            MainMenuFragment mainMenu = find("#fragMainMenu");

            ChoosePizzaSizeFragment choosePizzaSizeFragment = (ChoosePizzaSizeFragment) mainMenu.addPizza();
            find("#fragChoosePizzaSize");
            choosePizzaSizeFragment.choose(PizzaSize.SMALL);

            ChoosePizzaVariationFragment choosePizzaVariationFragment = (ChoosePizzaVariationFragment) choosePizzaSizeFragment.next();
            find("#fragChoosePizzaVariation");
            choosePizzaVariationFragment.next();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            closePopups();
            find("#fragChoosePizzaVariation");
        });
    }


    public void testChooseToppings(int numberOfToppings, PizzaSize size) {
        TestUtils.testOnUIThread(() ->
        {
            MainMenuFragment mainMenu = find("#fragMainMenu");

            ChoosePizzaSizeFragment choosePizzaSizeFragment = (ChoosePizzaSizeFragment) mainMenu.addPizza();
            find("#fragChoosePizzaSize");
            choosePizzaSizeFragment.choose(size);

            ChoosePizzaVariationFragment choosePizzaVariationFragment;
            if (size != PizzaSize.SMALL) {
                ChooseSplitFragment chooseSplitFragment = (ChooseSplitFragment) choosePizzaSizeFragment.next();
                find("#fragChooseSplit");
                chooseSplitFragment.choose(false);
                choosePizzaVariationFragment = (ChoosePizzaVariationFragment) chooseSplitFragment.next();
            } else {
                choosePizzaVariationFragment = (ChoosePizzaVariationFragment) choosePizzaSizeFragment.next();
            }

            find("#fragChoosePizzaVariation");
            choosePizzaVariationFragment.choose(0);//just Margherita again
            ChooseToppingsFragment chooseToppingsFragment = (ChooseToppingsFragment) choosePizzaVariationFragment.next();
            find("#fragChooseToppings");

            for (int i = 0; i < numberOfToppings; i++) {
                chooseToppingsFragment.addToppingSelector(0);
            }
            chooseToppingsFragment.abort();
        });
    }

    //für zu hohe Topping-Werte wird der "Topping hinzufügen" Button in der UI verschwinden, deshalb sind diese redundant.
    @Test
    public void choose0ToppingsTest() {
        testChooseToppings(0, PizzaSize.SMALL);
        testChooseToppings(0, PizzaSize.LARGE);
        testChooseToppings(0, PizzaSize.X_LARGE);
    }
    @Test
    public void choose2ToppingsTest() {
        testChooseToppings(2, PizzaSize.SMALL);
        testChooseToppings(2, PizzaSize.LARGE);
        testChooseToppings(2, PizzaSize.X_LARGE);
    }
    @Test
    public void choose3ToppingsTest() {
        testChooseToppings(3, PizzaSize.SMALL);
        testChooseToppings(3, PizzaSize.LARGE);
        testChooseToppings(3, PizzaSize.X_LARGE);
    }
    @Test
    public void choose5ToppingsTest() {
        testChooseToppings(5, PizzaSize.SMALL);
        testChooseToppings(5, PizzaSize.LARGE);
        testChooseToppings(5, PizzaSize.X_LARGE);    }
    @Test
    public void choose6ToppingsTest() {
        testChooseToppings(6, PizzaSize.SMALL);
        testChooseToppings(6, PizzaSize.LARGE);
        testChooseToppings(6, PizzaSize.X_LARGE);    }


    @Test
    public void PizzaDoneTest() {
        //Pizza wurde zum Cart hinzugefügt
        TestUtils.testOnUIThread(() ->
        {
            MainMenuFragment mainMenu = find("#fragMainMenu");

            ChoosePizzaSizeFragment choosePizzaSizeFragment = (ChoosePizzaSizeFragment) mainMenu.addPizza();
            find("#fragChoosePizzaSize");
            choosePizzaSizeFragment.choose(PizzaSize.SMALL);

            ChoosePizzaVariationFragment choosePizzaVariationFragment = (ChoosePizzaVariationFragment) choosePizzaSizeFragment.next();
            find("#fragChoosePizzaVariation");
            choosePizzaVariationFragment.choose(0);//just Margherita again

            ChooseToppingsFragment chooseToppingsFragment = (ChooseToppingsFragment) choosePizzaVariationFragment.next();
            find("#fragChooseToppings");

            chooseToppingsFragment.addToppingSelector(0);

            chooseToppingsFragment.next();
        });
    }

    @Test
    public void finishOrderTest()
    {

    }

}