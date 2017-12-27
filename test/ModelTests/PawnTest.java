package ModelTests;

import models.Field.*;
import models.Pawn.BasicPawn;
import models.Pawn.BluePawn;
import models.Pawn.Pawn;
import models.Pawn.RedPawn;
import models.PawnMove.BasicMove;
import models.Utility.Point;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PawnTest {
    public Pawn testPawn;
    public Field[][] testBoard;

    @Before
    public void setUp() throws Exception {
        testBoard = new Field[][]{
                {new BasicField(), new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new RedField(new BasicField()), new UnavailableField(new BasicField()), new RedField(new BasicField())},
                {new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new RedField(new BasicField()), new UnavailableField(new BasicField())},
                {new BasicField(), new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new RedField(new BasicField())},
                {new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField())},
                {new BasicField(), new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new BasicField(), new UnavailableField(new BasicField()), new BasicField()}
        };
        testPawn = new BluePawn(new BasicPawn(new Point(2, 4), new BasicMove()));
        testBoard[2][4].placePawn(testPawn);
    }

    @Test
    public void pawnShouldMoveUp(){
        printBoard();

        Point testMove = new Point(2, 2);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();

        assertTrue(result);
    }

    @Test
    public void pawnShouldMoveDown(){
        printBoard();

        Point testMove = new Point(2, 6);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();

        assertTrue(result);
    }

    @Test
    public void pawnShouldMoveLeft(){
        printBoard();

        Point testMove = new Point(0, 4);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();
        assertTrue(result);
    }

    @Test
    public void pawnShouldMoveRight(){
        printBoard();

        Point testMove = new Point(4, 4);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();

        assertTrue(result);
    }

    @Test
    public void canMoveOnUpLeft(){
        printBoard();

        Point testMove = new Point(1, 3);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();

        assertTrue(result);
    }

    @Test
    public void canMoveOnUpRight(){
        printBoard();

        Point testMove = new Point(3, 3);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();

        assertTrue(result);
    }

    @Test
    public void canMoveOnDownRight(){
        printBoard();

        Point testMove = new Point(3, 5);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();

        assertTrue(result);
    }

    @Test
    public void canMoveOnDownLeft(){
        printBoard();

        Point testMove = new Point(1, 5);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();

        assertTrue(result);
    }

    @Test
    public void canMoveLongOnCrossPlane(){
        testBoard[2][2].placePawn(new RedPawn(new BasicPawn(new Point(2, 2), new BasicMove())));
        printBoard();

        Point testMove = new Point(2, 0);

        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();
        assertTrue(result);
    }

    @Test
    public void canMoveLongOnXPlane(){
        testBoard[3][3].placePawn(new RedPawn(new BasicPawn(new Point(2, 2), new BasicMove())));
        printBoard();

        Point testMove = new Point(4, 2);

        Boolean result = testPawn.makeMove(testMove, testBoard);

        printBoard();
        assertTrue(result);
    }

    @Test
    public void canNotMoveOnNeighborField(){
        Point testMove = new Point(3, 4);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        assertFalse(result);
    }

    @Test
    public void tryGooutOfBoardShouldFail(){
        Point testMove = new Point(3, 5);
        Boolean result;

        result = testPawn.makeMove(testMove, testBoard);
        testMove = new Point(4, 6);
        result = testPawn.makeMove(testMove, testBoard);
        testMove = new Point(5, 7);
        result = testPawn.makeMove(testMove, testBoard);

        assertFalse(result);
    }

    @Test
    public void tryToMoveToNegativeCordsShouldFail(){
        Point testMove = new Point(-3, -4);
        Boolean result = testPawn.makeMove(testMove, testBoard);

        assertFalse(result);
    }

    @Test
    public void canNotEscapeFromDestinationColor(){
        Point testMove = new Point(1, 5);
        Boolean result;

        result = testPawn.makeMove(testMove, testBoard);
        testMove = new Point(3, 5);
        result = testPawn.makeMove(testMove, testBoard);

        assertFalse(result);

    }

    @Test
    public void canMoveInsideFinalColor(){
        Point testMove = new Point(1, 5);
        Boolean result;

        result = testPawn.makeMove(testMove, testBoard);
        testMove = new Point(0, 6);
        result = testPawn.makeMove(testMove, testBoard);

        assertTrue(result);
    }

    @Test
    public void canMoveOnOtherColors(){
        testBoard[3][3] = new BlueField(new BasicField());
        testBoard[2][2] = new BlackField(new BasicField());
        testBoard[1][1] = new YellowFIeld(new BasicField());
        Boolean result1, result2, result3, result4;

        Point tempPoint = new Point(3,3);
        result1 = testPawn.makeMove(tempPoint, testBoard);
        tempPoint = new Point(2, 2);
        result2 = testPawn.makeMove(tempPoint, testBoard);
        tempPoint = new Point(1, 1);
        result3 = testPawn.makeMove(tempPoint, testBoard);
        tempPoint = new Point(3, 1);
        result4 = testPawn.makeMove(tempPoint, testBoard);

        assertTrue(result1 && result2 && result3 && result4);
    }

    private void printBoard(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 5; j++){
                if(testBoard[j][i].getPawn() == null){
                    System.out.print("[_]");
                }
                else {
                    System.out.print("[X]");
                }
            }
            System.out.println();
        }
        System.out.println("[--------------]");
    }

}