package com.pizzaservice.api.data_access_objects;

import com.pizzaservice.api.buissness_objects.Customer;

import java.util.Collection;

/**
 * Created by philipp on 24.01.17.
 */
public interface CustomerDAO
{
    /**
     * Since customers will be identified by their phone number, this function must check
     * if there already exists a customer with the same phone number as the input customer.
     * @param customer
     * @return true if a new customer could be inserted (no duplicate phone numbers)
     */
    boolean addCustomer( Customer customer ) throws DataAccessException;

    Collection<Customer> getCustomers() throws DataAccessException;

    Customer findCustomerByPhoneNumber( String phoneNumber ) throws DataAccessException;

    Customer findCustomerById( long id ) throws DataAccessException;
}
