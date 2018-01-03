package models.Field;

import models.Pawn.Pawn;
import models.Utility.Point;

public class BasicField implements Field{
    protected String type;
    protected Pawn standingPawn;
    protected Point position;

    public BasicField(Point position){
        this.type = "";
        this.standingPawn = null;
        this.position = position;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Pawn getPawn() {
        return this.standingPawn;
    }

    @Override
    public void placePawn(Pawn pawn) {
        this.standingPawn = pawn;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}
