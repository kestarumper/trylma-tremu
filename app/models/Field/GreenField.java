package models.Field;

public class GreenField extends FieldDecorator {
    public GreenField(Field f) {
        super(f);
        this.decoratedField.setType("GRE");
    }

}
