package com.pizzaservice.db;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by philipp on 10.01.17.
 */
public class Database
{
    private ConnectionParams connectionParams;

    public Database( ConnectionParams connectionParams )
    {
        this.connectionParams = connectionParams;
    }

    /**
     * Executes a sql query by connecting to the database given with the connection parameters.
     * Arguments to the query can be passed by specifying a "?"-placeholder in the query.
     * The returned rows will be each processed by an onRowProcessedListener.
     *
     * This function throws standard SQLException for all code related to SQL, however it might throw a
     * custom Exception depending on the implementation of the OnRowProcessedListener callback.
     * Any exception thrown by this method (including the callback) gets thrown to the caller.
     *
     * @param query the SQL query code
     * @param args list of arguments which correspond to the "?"-placeholders
     * @param onRowProcessedListener row processing callback
     * @return true if there was at least one row returned from the database
     * @throws Exception, SQLException
     */
    public boolean query( String query, List<Object> args, OnRowProcessedListener onRowProcessedListener ) throws Exception
    {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try
        {
            connection = connect();
            pstmt = connection.prepareStatement( query );
            fillPreparedStatement( pstmt, args );
            pstmt.execute();

            rs = pstmt.getResultSet();
            if( !rs.next() )
                return false;

            do
            {
                Row row = new Row( rs );
                onRowProcessedListener.onRowProcessed( row );   // may throw custom exception
            }
            while( rs.next() );

            return true;
        }
        finally
        {
            closeQuietly( connection );
            closeQuietly( pstmt );
            closeQuietly( rs );
        }
    }

    public ConnectionParams getConnectionParams()
    {
        return connectionParams;
    }

    public void setConnectionParams( ConnectionParams connectionParams )
    {
        this.connectionParams = connectionParams;
    }

    private Connection connect() throws SQLException
    {
        try
        {
            Properties connectionProps = new Properties();
            connectionProps.put( "user", connectionParams.getUserName() );
            connectionProps.put( "password", connectionParams.getPassword() );

            return DriverManager.getConnection( "jdbc:" + connectionParams.getDbms() + "://" + connectionParams.getServerName() + ":" + connectionParams.getPortNumber() + "/" + connectionParams.getDatabaseName(), connectionProps );
        }
        catch( SQLException ex )
        {
            System.out.println( "SQLException: " + ex.getMessage() );
            System.out.println( "SQLState: " + ex.getSQLState() );
            System.out.println( "VendorError: " + ex.getErrorCode() );

            throw ex;
        }
    }

    private void fillPreparedStatement( PreparedStatement pstmt, List<Object> args ) throws SQLException
    {
        for( int i = 0; i < args.size(); i++ )
        {
            Object o = args.get( i );
            pstmt.setObject( i + 1, o );
        }
    }

    private void closeQuietly( Connection connection )
    {
        try { connection.close(); } catch( Exception e ) { /* ignore */ }
    }

    private void closeQuietly( PreparedStatement pstmt )
    {
        try { pstmt.close(); } catch( Exception e ) { /* ignore */ }
    }

    private void closeQuietly( ResultSet rs )
    {
        try { rs.close(); } catch( Exception e ) { /* ignore */ }
    }
}
