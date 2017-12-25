package models.Pawn;

public class RedPawn extends PawnDecorator {
    public RedPawn(Pawn entryPawn) {
        super(entryPawn);
        this.decoratedPawn.setColor("RED", "BLE");
    }
}
