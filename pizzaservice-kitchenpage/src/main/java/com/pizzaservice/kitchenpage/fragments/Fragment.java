package com.pizzaservice.kitchenpage.fragments;

import com.pizzaservice.common.Utils;
import com.pizzaservice.api.db.Database;
import com.pizzaservice.kitchenpage.Session;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;

/**
 * Created by philipp on 26.01.17.
 */
public class Fragment extends VBox
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

        this.rootPane = oldFragment.rootPane;
        this.session = oldFragment.session;
        this.database = oldFragment.database;

        Utils.load( getClass().getResource( filename ), this );
    }

    public void setup() {}

    public void setNewFragment( Fragment newFragment )
    {
        rootPane.getChildren().clear();
        rootPane.getChildren().add( newFragment );
        newFragment.setup();
    }
}
