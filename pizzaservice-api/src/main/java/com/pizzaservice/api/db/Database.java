package com.pizzaservice.api.db;

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
     * Executes an SQL GET by connecting to the database given by the connection parameters.
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
            pstmt.executeQuery();

            // get the rows to process
            rs = pstmt.getResultSet();
            if( rs == null || !rs.next() )
                return false;

            processRows( rs, onRowProcessedListener );
            return true;
        }
        finally
        {
            closeQuietly( rs );
            closeQuietly( pstmt );
            closeQuietly( connection );
        }
    }

    /**
     * Executes an SQL INSERT, UPDATE or DELETE by connecting to the database given by the connection parameters.
     * Arguments to the query can be passed by specifying a "?"-placeholder in the query.
     * The generated keys will be each processed by an onRowProcessedListener.
     *
     * This function throws standard SQLException for all code related to SQL, however it might throw a
     * custom Exception depending on the implementation of the OnRowProcessedListener callback.
     * Any exception thrown by this method (including the callback) gets thrown to the caller.
     *
     * @param update the SQL query code
     * @param args list of arguments which correspond to the "?"-placeholders
     * @param onRowProcessedListener callback to process generated keys
     * @return true if there was at least one generated key returned from the database
     * @throws Exception, SQLException
     */
    public boolean update( String update, List<Object> args, OnRowProcessedListener onRowProcessedListener ) throws Exception
    {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try
        {
            connection = connect();
            pstmt = connection.prepareStatement( update, Statement.RETURN_GENERATED_KEYS );
            fillPreparedStatement( pstmt, args );
            pstmt.executeUpdate();

            // get generated keys (e.g. auto generated ids) to process
            rs = pstmt.getGeneratedKeys();
            if( !rs.next() )
                return false;

            processRows( rs, onRowProcessedListener );
            return true;
        }
        finally
        {
            closeQuietly( rs );
            closeQuietly( pstmt );
            closeQuietly( connection );
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

            return DriverManager.getConnection(
                "jdbc:" + connectionParams.getDbms()
                + "://" + connectionParams.getServerName()
                + ":" + connectionParams.getPortNumber()
                + "/" + connectionParams.getDatabaseName()
                + "?autoReconnect=true&useSSL=false",
                connectionProps );
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

    private void processRows( ResultSet rs, OnRowProcessedListener onRowProcessedListener ) throws Exception
    {
        do
        {
            Row row = new Row( rs );
            onRowProcessedListener.onRowProcessed( row );   // may throw custom exception
        }
        while( rs.next() );
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
