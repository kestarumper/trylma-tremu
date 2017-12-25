package models.Pawn;

import models.Utility.Point;

public abstract class PawnDecorator implements Pawn{
    protected Pawn decoratedPawn;

    public PawnDecorator(Pawn entryPawn){
        this.decoratedPawn = entryPawn;
    }

    @Override
    public Point makeMove(Point destination) {
        return null;
    }

    @Override
    public void setColor(String color, String destColor) {
        decoratedPawn.setColor(color, destColor);
    }
}
