package models.Field;

public class BlueField extends FieldDecorator{

    public BlueField(Field f) {
        super(f);
        this.decoratedField.setType("BLE");
    }

}
