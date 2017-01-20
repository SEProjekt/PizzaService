package com.pizzaservice.orderpage.controller;

import com.pizzaservice.db.Database;
import com.pizzaservice.orderpage.Session;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by philipp on 16.01.17.
 */
public abstract class Controller
{
    protected Pane rootPane;
    protected Session session;
    protected Database database;

    public <ControllerType extends Controller> void setFragment( URL fragment ) throws IOException
    {
        FXMLLoader loader = new FXMLLoader( fragment );
        rootPane.getChildren().clear();
        rootPane.getChildren().add( loader.load() );
        ControllerType controller = loader.getController();
        controller.setup( rootPane, session, database );
    }

    public void setup( Pane rootPane, Session session, Database database )
    {
        this.rootPane = rootPane;
        this.session = session;
        this.database = database;

        onSetup();
    }

    protected abstract void onSetup();
}
