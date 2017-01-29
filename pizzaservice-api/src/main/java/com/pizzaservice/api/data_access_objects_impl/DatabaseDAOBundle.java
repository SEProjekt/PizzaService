package com.pizzaservice.api.data_access_objects_impl;

import com.pizzaservice.api.data_access_objects.*;
import com.pizzaservice.api.db.Database;

/**
 * Created by phili on 29.01.2017.
 */
public class DatabaseDAOBundle extends DAOBundle
{
    public DatabaseDAOBundle( Database database )
    {
        storeDAO = new StoreDatabaseDAO( database, this );
        orderDAO = new OrderDatabaseDAO( database, this );
        customerDAO = new CustomerDatabaseDAO( database, this );
        pizzaConfigurationDAO = new PizzaConfigurationDatabaseDAO( database, this );
        toppingDAO = new ToppingDatabaseDAO( database, this );
        pizzaVariationDAO = new PizzaVariationDatabaseDAO( database, this );
        recipeDAO = new RecipeDatabaseDAO( database, this );
        ingredientDAO = new IngredientDatabaseDAO( database, this );
    }
}
