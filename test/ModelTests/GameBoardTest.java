package ModelTests;

import models.GameBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorForBadSize(){
        GameBoard testBoard = new GameBoard(-1);
    }

}