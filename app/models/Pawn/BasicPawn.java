package models.Pawn;

import models.Utility.Point;

public class BasicPawn implements Pawn {
    protected Point position;
    protected String color;
    protected String destinationColor;

    public BasicPawn(Point p){
        this.position = p;
    }

    @Override
    public Point makeMove(Point destination) {
        return null;
    }

    @Override
    public void setColor(String color, String destColor) {
        this.color = color;
        this.destinationColor = destColor;
    }
}
