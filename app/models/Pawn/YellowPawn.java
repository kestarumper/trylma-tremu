package models.Pawn;

public class YellowPawn extends PawnDecorator {
    public YellowPawn(Pawn entryPawn) {
        super(entryPawn);
        this.decoratedPawn.setColor("YEL", "GRE");
    }
}
