package models.Pawn;

import models.Field.Field;
import models.Strategies.MoveStrategy;
import models.User;
import models.Utility.Point;

public class BasicPawn implements Pawn {
    protected Point position;
    protected String color;
    protected String destinationColor;
    protected MoveStrategy moveStrategy;
    protected boolean isOnColor;

    public BasicPawn(Point p, MoveStrategy strategy){
        this.position = p;
        this.moveStrategy = strategy;
        this.isOnColor = false;
    }

    @Override
    public Boolean makeMove(Point destination, Field[][] board, User currentUser) {
        Point tempPoint = moveStrategy.doMove(this.position, destination, board, currentUser);

        if(position.getX() != tempPoint.getX() || position.getY() != tempPoint.getY()){
            currentUser.setLastMove(this.position);
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

    @Override
    public boolean isOnColor() {
        return this.isOnColor;
    }

    @Override
    public void setOnColor() {
        this.isOnColor = true;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public boolean checkMove(Point destination, Field[][] board, User currentUser) {
        Point tempPoint = moveStrategy.doMove(this.position, destination, board, currentUser);

        if(position.getX() != tempPoint.getX() || position.getY() != tempPoint.getY()){
            currentUser.setLastMove(tempPoint);
            return true;
        }
        return false;

    }
}
