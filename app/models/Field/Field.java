package models.Field;
/*
    Interface which is needed for creating decorated fields.
    Type of fields:
       - Basic Field - ""
       - Blue Field - "BLE" - Located at Top
       - Black Field - "BCK" - Located at Bottom
       - Green Field - "GRE" - Located at Left-Up
       - White Field - "WHT" - Located at Left-Down
       - Yellow Field - "YEL" - Located at Right-Up
       - Red Field - "RED" - Located at Right-Down
       - Unavailable Field - "UNV"
 */
public interface Field {
    public String getType();
}
