package models.PawnMove;

import models.Strategies.MoveStrategy;
import models.Utility.Point;

public class BasicMove implements MoveStrategy {
    @Override
    public Point doMove(Point start, Point desired) {
        return new Point(-1, -1);
    }
}
