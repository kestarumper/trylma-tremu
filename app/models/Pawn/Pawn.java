package models.Pawn;

import models.Field.Field;
import models.Utility.Point;

public interface Pawn {
    public Boolean makeMove(Point destination, Field[][] board);
    public void setColor(String color, String destColor);
    public String getDesiredColor();
    public String getColor();
}
