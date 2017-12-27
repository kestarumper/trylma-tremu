package models.PawnMove;

import models.Field.Field;
import models.Pawn.Pawn;
import models.Strategies.MoveStrategy;
import models.Utility.Point;

import static java.lang.Math.abs;

public class BasicMove implements MoveStrategy {
    protected Boolean isOnColor;

    public BasicMove(){
        this.isOnColor = false;
    }

    @Override
    public Point doMove(Point start, Point desired, Field[][] board) {
        if(isOnColor){
            return doMoveOnColor(start, desired, board);
        }
        else{
            return doMoveNormal(start, desired, board);
        }
    }

    protected Point doMoveOnColor(Point start, Point desired, Field[][] board){

        return start;
    }

    protected Point doMoveNormal(Point start, Point desired, Field[][] board){
        if(start.getX() == desired.getX() || start.getY() == start.getY()){
            if(abs(start.getX() - desired.getX()) == 2 || abs(start.getY() - desired.getY()) == 2){
                //Normal move on cross plan
                if(board[desired.getX()][desired.getY()].getPawn() == null){
                    if(isMovePossibleOnColorChange(start, desired, board)){
                        if(hasColorChanged(start, desired, board)){
                            this.isOnColor = true;
                        }
                        return desired;
                    }
                }
            }
            else{
                //Longer move on cross plan

            }
        }
        else{

        }

        return start;
    }

    protected Boolean hasColorChanged(Point start, Point desired, Field[][] board){
        if(!board[start.getX()][start.getX()].getType().equals(board[desired.getX()][desired.getY()])){
            return true;
        }

        return false;
    }

    protected Boolean isMovePossibleOnColorChange(Point start, Point desired, Field[][] board){
        Pawn tempPawn = board[start.getX()][start.getY()].getPawn();
        String color = board[desired.getX()][desired.getY()].getType();

        if(color.equals(tempPawn.getDesiredColor()) || color.equals("")){
            return true;
        }

        return false;
    }
}
