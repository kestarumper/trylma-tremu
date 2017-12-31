package models.Pawn;

import models.Field.Field;
import models.User;
import models.Utility.Point;

public abstract class PawnDecorator implements Pawn{
    protected Pawn decoratedPawn;

    public PawnDecorator(Pawn entryPawn){
        this.decoratedPawn = entryPawn;
    }

    @Override
    public Boolean makeMove(Point destination, Field[][] board, User currentUser) {
        if(destination.getX() < 0 || destination.getY() < 0 || destination.getX() >= board.length ||destination.getY() >= board[0].length){
            return false;
        }
        return this.decoratedPawn.makeMove(destination, board, currentUser);
    }

    @Override
    public void setColor(String color, String destColor) {
        decoratedPawn.setColor(color, destColor);
    }

    @Override
    public String getDesiredColor(){
        return this.decoratedPawn.getDesiredColor();
    }

    @Override
    public String getColor(){
        return this.decoratedPawn.getColor();
    }

    @Override
    public boolean isOnColor() {
        return this.decoratedPawn.isOnColor();
    }

    @Override
    public void setOnColor() {
        this.decoratedPawn.setOnColor();
    }
}
