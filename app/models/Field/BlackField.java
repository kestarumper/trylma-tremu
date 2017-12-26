package models.Field;

public class BlackField extends FieldDecorator{

    public BlackField(Field f) {
        super(f);
        this.decoratedField.setType("BCK");
    }
}
