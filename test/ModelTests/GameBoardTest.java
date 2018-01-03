package ModelTests;

import models.Field.BasicField;
import models.Field.Field;
import models.Field.UnavailableField;
import models.GameBoard;
import models.GameBoardGenerators.SixPlayerBoard;
import models.PawnMove.BasicMove;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorForBadSize(){
        GameBoard testBoard = new GameBoard(-1, new BasicMove(), new SixPlayerBoard());
    }

    @Test
    public void shouldPrintMap(){
        GameBoard board = new GameBoard(5, new BasicMove(), new SixPlayerBoard());
        System.out.println(board.printBoard());
        assertNotNull(board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeCordShouldRaiseError(){
        GameBoard board = new GameBoard(3, new BasicMove(), new SixPlayerBoard());
        String test = board.getFieldType(-15, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toBigNumberOutOfBoundsOfArrayRaiseException(){
        GameBoard board = new GameBoard(3, new BasicMove(), new SixPlayerBoard());
        String test = board.getFieldType(5433, 543);
    }

    @Test
    public void zeroCordSHouldBeUnavilable(){
        GameBoard board = new GameBoard(2, new BasicMove(), new SixPlayerBoard());
        Field test = new UnavailableField(new BasicField());
        assertEquals(board.getFieldType(0, 0), test.getType());
    }

}