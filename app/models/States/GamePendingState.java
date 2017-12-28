package models.States;

public class GamePendingState implements GameSessionState {
    @Override
    public void moveToNextPhase(GameSessionContext context) {
        context.setState(new GameInProgressState());
    }
}
