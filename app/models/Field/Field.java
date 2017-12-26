package models.Field;

import models.Pawn.Pawn;

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
       - Unavailable Field - "UNV" - all fields you can't stand on!
 */
public interface Field {
    public String getType();
    public void setType(String type);
    public Pawn getPawn();
    public void placePawn(Pawn pawn);
}
