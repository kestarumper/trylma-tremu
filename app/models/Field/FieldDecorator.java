package models.Field;

import models.Pawn.Pawn;
import models.Utility.Point;

public abstract class FieldDecorator implements Field {

    protected Field decoratedField;

    public FieldDecorator(Field f){
        this.decoratedField = f;
    }

    @Override
    public String getType() {
        return this.decoratedField.getType();
    }

    @Override
    public void setType(String type) {
        this.decoratedField.setType(type);
    }

    @Override
    public Pawn getPawn() {
        return this.decoratedField.getPawn();
    }

    @Override
    public void placePawn(Pawn pawn) {
        this.decoratedField.placePawn(pawn);
    }

    @Override
    public Point getPosition() {
        return this.decoratedField.getPosition();
    }
}
