package com.pizzaservice.api.db;

/**
 * Created by philipp on 19.01.17.
 */
public interface OnRowProcessedListener
{
    void onRowProcessed( Row row ) throws Exception;
}