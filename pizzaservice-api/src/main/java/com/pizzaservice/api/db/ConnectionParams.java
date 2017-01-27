package com.pizzaservice.api.db;

/**
 * Created by philipp on 11.01.17.
 */
public class ConnectionParams
{
    private String userName;
    private String password;
    private String dbms;
    private String serverName;
    private String portNumber;
    private String databaseName;

    public ConnectionParams( String userName, String password, String dbms, String serverName, String portNumber, String databaseName )
    {
        this.userName = userName;
        this.password = password;
        this.dbms = dbms;
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.databaseName = databaseName;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDbms()
    {
        return dbms;
    }

    public String getServerName()
    {
        return serverName;
    }

    public String getPortNumber()
    {
        return portNumber;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }
}
