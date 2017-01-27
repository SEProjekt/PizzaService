package com.pizzaservice.customerpage.fragments;

import com.pizzaservice.api.db.Database;
import com.pizzaservice.customerpage.Session;
import com.pizzaservice.common.Utils;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by philipp on 23.01.17.
 */
public abstract class Fragment extends VBox
{
    protected Pane rootPane;
    protected Session session;
    protected Database database;

    public Fragment( String filename, Pane rootPane, Session session, Database database )
    {
        super();

        this.rootPane = rootPane;
        this.session = session;
        this.database = database;

        Utils.load( getClass().getResource( filename ), this );
    }

    public Fragment( String filename, Fragment oldFragment )
    {
        super();

        this.rootPane = oldFragment.getRootPane();
        this.session = oldFragment.getSession();
        this.database = oldFragment.getDatabase();

        Utils.load( getClass().getResource( filename ), this );
    }

    public void setup() {}

    public Fragment setNewFragment( Fragment newFragment )
    {
        if( newFragment == null )
            return null;

        rootPane.getChildren().clear();
        rootPane.getChildren().add( newFragment );
        newFragment.setup();

        return newFragment;
    }

    public Pane getRootPane()
    {
        return rootPane;
    }

    public void setRootPane( Pane rootPane )
    {
        this.rootPane = rootPane;
    }

    public Session getSession()
    {
        return session;
    }

    public void setSession( Session session )
    {
        this.session = session;
    }

    public Database getDatabase()
    {
        return database;
    }

    public void setDatabase( Database database )
    {
        this.database = database;
    }
}
