package models.Field;
/*
    Interface which is needed for creating decorated fields.
    Type of fields:
       - Basic Field - ""
       - Blue Field - "BLE"
       - Black Field - "BCK"
       - Green Field - "GRE"
       - White Field - "WHT"
       - Yellow Field - "YEL"
       - Red Field - "RED"
 */
public interface Field {
    public String getType();
}
