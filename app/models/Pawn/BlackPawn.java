package models.Pawn;

public class BlackPawn extends PawnDecorator {
    public BlackPawn(Pawn entryPawn) {
        super(entryPawn);
        this.decoratedPawn.setColor("BCK", "WHT");
    }
}
