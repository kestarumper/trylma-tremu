package models.Field;

public class YellowFIeld extends FieldDecorator {
    public YellowFIeld(Field f) {
        super(f);
        this.decoratedField.setType("YEL");
    }

}
