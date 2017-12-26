package models.Field;

public class UnavailableField extends FieldDecorator {
    public UnavailableField(Field f) {
        super(f);
        this.decoratedField.setType("UNV");
    }

}
