package models.States;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameSessionContextTest {
    GameSessionContext testContext;

    @Before
    public void setUp(){
        testContext = new GameSessionContext();
    }

    @Test
    public void basicStateShouldBePending(){
        assertTrue(testContext.getState() instanceof GamePendingState);
    }

    @Test
    public void stateShouldChangeToInGame(){
        testContext.nextState();

        assertTrue(testContext.getState() instanceof GameInProgressState);
    }

    @Test
    public void stateShouldChangeToGameIsOver(){
        testContext.nextState();
        testContext.nextState();

        assertTrue(testContext.getState() instanceof GameIsOverState);
    }

    @Test
    public void stateAfterGameIsOverShouldNotChange(){
        testContext.nextState();
        testContext.nextState();
        testContext.nextState();

        assertTrue(testContext.getState() instanceof GameIsOverState);
    }

}