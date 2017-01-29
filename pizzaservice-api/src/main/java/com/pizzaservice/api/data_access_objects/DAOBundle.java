package com.pizzaservice.api.data_access_objects;

/**
 * Created by phili on 29.01.2017.
 */
public abstract class DAOBundle
{
    protected StoreDAO storeDAO;
    protected OrderDAO orderDAO;
    protected CustomerDAO customerDAO;
    protected PizzaConfigurationDAO pizzaConfigurationDAO;
    protected ToppingDAO toppingDAO;
    protected PizzaVariationDAO pizzaVariationDAO;
    protected RecipeDAO recipeDAO;
    protected IngredientDAO ingredientDAO;

    public StoreDAO getStoreDAO()
    {
        return storeDAO;
    }

    public OrderDAO getOrderDAO()
    {
        return orderDAO;
    }

    public CustomerDAO getCustomerDAO()
    {
        return customerDAO;
    }

    public PizzaConfigurationDAO getPizzaConfigurationDAO()
    {
        return pizzaConfigurationDAO;
    }

    public ToppingDAO getToppingDAO()
    {
        return toppingDAO;
    }

    public PizzaVariationDAO getPizzaVariationDAO()
    {
        return pizzaVariationDAO;
    }

    public RecipeDAO getRecipeDAO()
    {
        return recipeDAO;
    }

    public IngredientDAO getIngredientDAO()
    {
        return ingredientDAO;
    }
}
