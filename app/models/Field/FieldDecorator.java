package models.Field;

public class FieldDecorator implements Field {

    protected Field field;

    public FieldDecorator(Field f){
        this.field = f;
    }

    @Override
    public String getType() {
        return this.field.getType();
    }
}
