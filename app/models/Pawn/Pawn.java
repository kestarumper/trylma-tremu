package models.Pawn;

import models.Utility.Point;

public interface Pawn {
    public Point makeMove(Point destination);
    public void setColor(String color, String destColor);
}
