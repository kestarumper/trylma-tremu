package models.Pawn;

public class WhitePawn extends PawnDecorator {
    public WhitePawn(Pawn entryPawn) {
        super(entryPawn);
        this.decoratedPawn.setColor("WHT", "BCK");
    }
}
