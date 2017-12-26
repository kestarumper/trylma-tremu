package models.Field;

public class RedField extends FieldDecorator {
    public RedField(Field f) {
        super(f);
        this.decoratedField.setType("RED");
    }
}
