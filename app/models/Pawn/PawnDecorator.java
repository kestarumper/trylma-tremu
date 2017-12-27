package models.Pawn;

import models.Field.Field;
import models.Utility.Point;

public abstract class PawnDecorator implements Pawn{
    protected Pawn decoratedPawn;

    public PawnDecorator(Pawn entryPawn){
        this.decoratedPawn = entryPawn;
    }

    @Override
    public Boolean makeMove(Point destination, Field[][] board) {
        if(destination.getX() < 0 || destination.getY() < 0 || destination.getX() >= board.length ||destination.getY() >= board[0].length){
            return false;
        }
        return this.decoratedPawn.makeMove(destination, board);
    }

    @Override
    public void setColor(String color, String destColor) {
        decoratedPawn.setColor(color, destColor);
    }

    @Override
    public String getDesiredColor(){
        return this.decoratedPawn.getDesiredColor();
    }
}
