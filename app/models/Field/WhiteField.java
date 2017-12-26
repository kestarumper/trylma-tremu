package models.Field;

public class WhiteField extends FieldDecorator {
    public WhiteField(Field f) {
        super(f);
        this.decoratedField.setType("WHT");
    }

}
