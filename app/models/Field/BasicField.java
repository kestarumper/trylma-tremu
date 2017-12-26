package models.Field;

import models.Pawn.Pawn;

public class BasicField implements Field{
    protected String type;
    protected Pawn standingPawn;

    public BasicField(){
        this.type = "";
        this.standingPawn = null;
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
}
