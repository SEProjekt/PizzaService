package com.pizzaservice.common.test;

import com.mysql.jdbc.AssertionFailedException;
import javafx.application.Platform;

import static org.junit.Assert.assertTrue;

/**
 * Created by philipp on 27.01.17.
 */
public class TestUtils
{
    public static class Result
    {
        private AssertionFailedException assertionFailedException = null;

        public Result() {}

        public AssertionFailedException getAssertionFailedException()
        {
            return assertionFailedException;
        }

        public void setAssertionFailedException( AssertionFailedException assertionFailedException )
        {
            this.assertionFailedException = assertionFailedException;
        }
    }

    public interface UICallback
    {
        void call() throws AssertionFailedException;
    }

    public static void testOnUIThread( UICallback uiCallback, long waitMilliseconds )
    {
        Result result = new Result();

        Platform.runLater( () ->
        {
            try { uiCallback.call(); }
            catch( AssertionFailedException e ) { result.setAssertionFailedException( e ); }
        } );

        try { Thread.sleep( waitMilliseconds ); }
        catch( InterruptedException e ) { new RuntimeException(); }

        if( result.assertionFailedException != null )
            throw result.getAssertionFailedException();
    }

    public static void testOnUIThread( UICallback uiCallback )
    {
        testOnUIThread( uiCallback, 200 );
    }
}
