package com.pizzaservice.common.test;

import com.pizzaservice.api.buissness_objects.PizzaConfiguration;
import com.pizzaservice.api.buissness_objects.PizzaSize;
import com.pizzaservice.api.buissness_objects.PizzaVariation;
import com.pizzaservice.api.buissness_objects.Recipe;

import java.util.ArrayList;

/**
 * Created by philipp on 27.01.17.
 */
public class DummyFactory
{
    public static PizzaConfiguration dummyPizzaConfiguration()
    {
        PizzaConfiguration configuration = new PizzaConfiguration();
        configuration.setId( 0 );
        configuration.setOrder( null );
        configuration.setPizzaSize( PizzaSize.SMALL );
        configuration.setSplit( false );
        configuration.setPizzaVariation1( dummyPizzaVariation() );
        configuration.setPizzaVariation2( null );
        configuration.setToppings( new ArrayList<>() );
        return configuration;
    }

    public static PizzaVariation dummyPizzaVariation()
    {
        PizzaVariation variation = new PizzaVariation();
        variation.setId( 1 );
        variation.setName( "Margherita" );
        variation.setRecipeSmall( dummyRecipe() );
        variation.setRecipeLarge( dummyRecipe() );
        variation.setRecipeXLarge( dummyRecipe() );
        variation.setPriceSmall( 3 );
        variation.setPriceLarge( 6 );
        variation.setPriceXLarge( 9 );
        return variation;
    }

    public static Recipe dummyRecipe()
    {
        Recipe recipe = new Recipe();
        recipe.setId( 1 );
        recipe.setEntries( new ArrayList<>() );
        return recipe;
    }
}
