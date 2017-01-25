package com.pizzaservice.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Created by philipp on 19.01.17.
 *
 * Wrapper around java.sql.ResultSet for retrieving values of a row obtained by a query.
 */
public class Row
{
    private ResultSet rs;

    public Row( ResultSet rs )
    {
        this.rs = rs;
    }

    public int getInt( int columnIndex ) throws SQLException
    {
        return rs.getInt( columnIndex );
    }

    public int getInt( String columnLabel ) throws SQLException
    {
        return rs.getInt( columnLabel );
    }
    
    public long getLong( int columnIndex ) throws SQLException
    {
        return rs.getLong( columnIndex );
    }

    public long getLong( String columnLabel ) throws SQLException
    {
        return rs.getLong( columnLabel );
    }

    public float getFloat( int columnIndex ) throws SQLException
    {
        return rs.getFloat( columnIndex );
    }

    public float getFloat( String columnLabel ) throws SQLException
    {
        return rs.getFloat( columnLabel );
    }

    public String getString( int columnIndex ) throws SQLException
    {
        return rs.getString( columnIndex );
    }

    public String getString( String columnLabel ) throws SQLException
    {
        return rs.getString( columnLabel );
    }

    public Time getTime( int columnIndex ) throws SQLException
    {
        return rs.getTime( columnIndex );
    }

    public Time getTime( String columnLabel ) throws SQLException
    {
        return rs.getTime( columnLabel );
    }

    public boolean getBoolean( int columnIndex ) throws SQLException
    {
        return rs.getBoolean( columnIndex );
    }

    public boolean getBoolean( String columnLabel ) throws SQLException
    {
        return rs.getBoolean( columnLabel );
    }
}
