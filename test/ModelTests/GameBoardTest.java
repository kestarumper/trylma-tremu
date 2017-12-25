package ModelTests;

import models.Field.Field;
import models.Field.UnavailableField;
import models.GameBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorForBadSize(){
        GameBoard testBoard = new GameBoard(-1);
    }

    @Test
    public void shouldPrintMap(){
        GameBoard board = new GameBoard(5);
        System.out.println(board.printBoard());
        assertNotNull(board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeCordShouldRaiseError(){
        GameBoard board = new GameBoard(3);
        String test = board.getField(-15, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toBigNumberOutOfBoundsOfArrayRaiseException(){
        GameBoard board = new GameBoard(3);
        String test = board.getField(5433, 543);
    }

    @Test
    public void zeroCordSHouldBeUnavilable(){
        GameBoard board = new GameBoard(2);
        Field test = new UnavailableField();
        assertEquals(board.getField(0, 0), test.getType());
    }

}