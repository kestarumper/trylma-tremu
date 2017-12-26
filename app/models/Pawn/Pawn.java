package models.Pawn;

import models.Field.Field;
import models.Utility.Point;

public interface Pawn {
    public Point makeMove(Point destination, Field[][] board);
    public void setColor(String color, String destColor);
}
