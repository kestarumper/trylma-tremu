package models.States;

public interface GameSessionState {
    public void moveToNextPhase(GameSessionContext context);
}
