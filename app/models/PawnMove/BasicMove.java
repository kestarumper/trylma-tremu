package models.PawnMove;

import models.Field.Field;
import models.Pawn.Pawn;
import models.Strategies.MoveStrategy;
import models.User;
import models.Utility.Point;

import static java.lang.Math.abs;

public class BasicMove implements MoveStrategy {
    protected boolean firstPawn;

    @Override
    public Point doMove(Point start, Point desired, Field[][] board, User currentUser) {
        if(currentUser.getCurrentPawn() == null){
            this.firstPawn = true;
        }
        else{
            this.firstPawn = false;
        }

        if(board[desired.getX()][desired.getY()].getPawn() == null) {
            if (board[start.getX()][start.getY()].getPawn().isOnColor()) {
                return doMoveOnColor(start, desired, board);
            } else {
                return doMoveNormal(start, desired, board);
            }
        }
        else{
            return start;
        }
    }

    protected Point doMoveOnColor(Point start, Point desired, Field[][] board){
        Pawn tempPawn = board[start.getX()][start.getY()].getPawn();

        if(board[desired.getX()][desired.getY()].getType().equals(tempPawn.getDesiredColor())){
            return doMoveNormal(start, desired, board);
        }

        return start;
    }

    protected Point doMoveNormal(Point start, Point desired, Field[][] board){
        if(start.getX() != desired.getX() && start.getY() == desired.getY()){
            if(abs(start.getX() - desired.getX()) == 2 && this.firstPawn){
                //Normal move on cross plan
                if(board[desired.getX()][desired.getY()].getPawn() == null){
                        if(hasColorChanged(start, desired, board)){
                            board[start.getX()][start.getY()].getPawn().setOnColor();
                        }
                        return desired;

                }
            }
            else if(abs(start.getX() - desired.getX()) == 4){
                //Longer move on cross plan
                if(hasPawnInMiddle(start, desired, board)){
                    if(hasColorChanged(start, desired, board)){
                        board[start.getX()][start.getY()].getPawn().setOnColor();
                    }
                    return desired;
                }
            }
        }
        else{
            //Move on X plane
            if(abs(start.getX() - desired.getX()) == 1 && abs(start.getY() - desired.getY()) == 1 && this.firstPawn){
                //Short move on X plane
                if(board[desired.getX()][desired.getY()].getPawn() == null){
                        if(hasColorChanged(start, desired, board)){
                            board[start.getX()][start.getY()].getPawn().setOnColor();
                        }
                        return desired;
                }

            }
            else if(abs(start.getX() - desired.getX()) == 2 && abs(start.getY() - desired.getY()) == 2){
                //Long move on X plane
                if(hasPawnInMiddle(start, desired, board)){
                    if(hasColorChanged(start, desired, board)){
                        board[start.getX()][start.getY()].getPawn().setOnColor();
                    }
                    return desired;
                }
            }
        }

        return start;
    }

    protected Boolean hasColorChanged(Point start, Point desired, Field[][] board){
        Pawn tempPawn = board[start.getX()][start.getY()].getPawn();
        String color = board[desired.getX()][desired.getY()].getType();

        if(tempPawn.getDesiredColor().equals(color)){
            return true;
        }

        return false;
    }

    protected Boolean hasPawnInMiddle(Point start, Point desired, Field[][] board){
        int newX, newY;
        newX = (start.getX() + desired.getX()) / 2;
        newY = (start.getY() + desired.getY()) / 2;

        if(board[newX][newY].getPawn() != null){
            return true;
        }

        return false;
    }
}
