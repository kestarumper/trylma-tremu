package models.Pawn;

import models.Field.Field;
import models.Strategies.MoveStrategy;
import models.Utility.Point;

public class BasicPawn implements Pawn {
    protected Point position;
    protected String color;
    protected String destinationColor;
    protected MoveStrategy moveStrategy;

    public BasicPawn(Point p, MoveStrategy strategy){
        this.position = p;
        this.moveStrategy = strategy;
    }

    @Override
    public Boolean makeMove(Point destination, Field[][] board) {
        Point tempPoint = moveStrategy.doMove(this.position, destination, board);

        if(position.getX() != tempPoint.getX() || position.getY() != tempPoint.getY()){
            board[this.position.getX()][this.position.getY()].placePawn(null);
            this.position = tempPoint;
            board[this.position.getX()][this.position.getY()].placePawn(this);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void setColor(String color, String destColor) {
        this.color = color;
        this.destinationColor = destColor;
    }

    @Override
    public String getDesiredColor() {
        return this.destinationColor;
    }

    @Override
    public String getColor() {
        return this.color;
    }
}
