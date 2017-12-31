package models.Pawn;

import models.Field.Field;
import models.User;
import models.Utility.Point;

public interface Pawn {
    public Boolean makeMove(Point destination, Field[][] board, User currentUser);
    public void setColor(String color, String destColor);
    public String getDesiredColor();
    public String getColor();
    public boolean isOnColor();
    public void setOnColor();
}
