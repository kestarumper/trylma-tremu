package models.Strategies;

import models.Field.Field;
import models.Utility.Point;

public interface MoveStrategy {
    public Point doMove(Point start, Point desired, Field[][] board);
}
