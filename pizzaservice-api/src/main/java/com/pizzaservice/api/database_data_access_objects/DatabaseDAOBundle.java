package com.pizzaservice.api.database_data_access_objects;

import com.pizzaservice.api.data_access_objects.*;
import com.pizzaservice.api.db.Database;

/**
 * Created by phili on 29.01.2017.
 */
public class DatabaseDAOBundle implements DAOBundle
{
    private StoreDatabaseDAO storeDatabaseDAO;
    private OrderDatabaseDAO orderDatabaseDAO;
    private CustomerDatabaseDAO customerDatabaseDAO;
    private PizzaConfigurationDatabaseDAO pizzaConfigurationDatabaseDAO;
    private ToppingDatabaseDAO toppingDatabaseDAO;
    private PizzaVariationDatabaseDAO pizzaVariationDatabaseDAO;
    private RecipeDatabaseDAO recipeDatabaseDAO;
    private IngredientDatabaseDAO ingredientDatabaseDAO;

    public DatabaseDAOBundle( Database database )
    {
        storeDatabaseDAO = new StoreDatabaseDAO( database, this );
        orderDatabaseDAO = new OrderDatabaseDAO( database, this );
        customerDatabaseDAO = new CustomerDatabaseDAO( database, this );
        pizzaConfigurationDatabaseDAO = new PizzaConfigurationDatabaseDAO( database, this );
        toppingDatabaseDAO = new ToppingDatabaseDAO( database, this );
        pizzaVariationDatabaseDAO = new PizzaVariationDatabaseDAO( database, this );
        recipeDatabaseDAO = new RecipeDatabaseDAO( database, this );
        ingredientDatabaseDAO = new IngredientDatabaseDAO( database, this );
    }

    @Override
    public StoreDAO getStoreDAO()
    {
        return storeDatabaseDAO;
    }

    @Override
    public OrderDAO getOrderDAO()
    {
        return orderDatabaseDAO;
    }

    @Override
    public CustomerDAO getCustomerDAO()
    {
        return customerDatabaseDAO;
    }

    @Override
    public PizzaConfigurationDAO getPizzaConfigurationDAO()
    {
        return pizzaConfigurationDatabaseDAO;
    }

    @Override
    public ToppingDAO getToppingDAO()
    {
        return toppingDatabaseDAO;
    }

    @Override
    public PizzaVariationDAO getPizzaVariationDAO()
    {
        return pizzaVariationDatabaseDAO;
    }

    @Override
    public RecipeDAO getRecipeDAO()
    {
        return recipeDatabaseDAO;
    }

    @Override
    public IngredientDAO getIngredientDAO()
    {
        return ingredientDatabaseDAO;
    }

    public StoreDatabaseDAO getStoreDatabaseDAO()
    {
        return storeDatabaseDAO;
    }

    public OrderDatabaseDAO getOrderDatabaseDAO()
    {
        return orderDatabaseDAO;
    }

    public CustomerDatabaseDAO getCustomerDatabaseDAO()
    {
        return customerDatabaseDAO;
    }

    public PizzaConfigurationDatabaseDAO getPizzaConfigurationDatabaseDAO()
    {
        return pizzaConfigurationDatabaseDAO;
    }

    public ToppingDatabaseDAO getToppingDatabaseDAO()
    {
        return toppingDatabaseDAO;
    }

    public PizzaVariationDatabaseDAO getPizzaVariationDatabaseDAO()
    {
        return pizzaVariationDatabaseDAO;
    }

    public RecipeDatabaseDAO getRecipeDatabaseDAO()
    {
        return recipeDatabaseDAO;
    }

    public IngredientDatabaseDAO getIngredientDatabaseDAO()
    {
        return ingredientDatabaseDAO;
    }
}
