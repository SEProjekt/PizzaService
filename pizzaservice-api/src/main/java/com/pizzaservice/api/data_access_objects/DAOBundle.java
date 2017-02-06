package com.pizzaservice.api.data_access_objects;

/**
 * Created by phili on 29.01.2017.
 */
public interface DAOBundle
{
    StoreDAO getStoreDAO();

    OrderDAO getOrderDAO();

    CustomerDAO getCustomerDAO();

    PizzaConfigurationDAO getPizzaConfigurationDAO();

    ToppingDAO getToppingDAO();

    PizzaVariationDAO getPizzaVariationDAO();

    RecipeDAO getRecipeDAO();

    IngredientDAO getIngredientDAO();
}
