package models.Pawn;

public class BluePawn extends PawnDecorator {
    public BluePawn(Pawn entryPawn) {
        super(entryPawn);
        this.decoratedPawn.setColor("BLE", "RED");
    }
}
