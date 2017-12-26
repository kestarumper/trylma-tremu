package models.PawnMove;

import models.Field.Field;
import models.Strategies.MoveStrategy;
import models.Utility.Point;

import static java.lang.Math.abs;

public class BasicMove implements MoveStrategy {
    @Override
    public Point doMove(Point start, Point desired, Field[][] board) {
        if(start.getX() == desired.getX() || start.getY() == start.getY()){
            if(abs(start.getX() - desired.getX()) == 2 || abs(start.getY() - desired.getY()) == 2){
                //TODO: Check is there is another Pawn on field
                return desired;
            }
        }

        return new Point(-1, -1);
    }
}
