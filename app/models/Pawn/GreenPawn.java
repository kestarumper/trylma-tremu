package models.Pawn;

public class GreenPawn extends PawnDecorator{
    public GreenPawn(Pawn entryPawn) {
        super(entryPawn);
        this.decoratedPawn.setColor("GRE", "YEL");
    }
}
