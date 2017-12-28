package models.States;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerContextTest {
    PlayerContext testContext;

    @Before
    public void setUp(){
        testContext = new PlayerContext();
    }

    @Test
    public void stateShouldChange(){
        PlayerContext basicContext = new PlayerContext();
        testContext.toggleFocus();

        assertFalse(testContext.getState() instanceof PlayerWaitingState);
    }

    @Test
    public void stateDoubleToggleShouldNotChange(){
        testContext.toggleFocus();
        testContext.toggleFocus();

        assertTrue(testContext.getState() instanceof PlayerWaitingState);
    }

    @Test
    public void stateShouldBeWinning(){
        testContext.setAsWinner();

        assertTrue(testContext.getState() instanceof PlayerWinState);
    }

    @Test
    public void stateAfterWinShouldNotChange(){
        testContext.setAsWinner();
        testContext.toggleFocus();

        assertTrue(testContext.getState() instanceof PlayerWinState);
    }

}