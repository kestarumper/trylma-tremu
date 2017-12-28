package models.States;

public class GameInProgressState implements GameSessionState {
    @Override
    public void moveToNextPhase(GameSessionContext context) {
        context.setState(new GameIsOverState());
    }
}
