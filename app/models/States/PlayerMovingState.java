package models.States;

public class PlayerMovingState implements PlayerState {
    @Override
    public void toggleState(PlayerContext context) {
        context.setState(new PlayerWaitingState());
    }

    @Override
    public void becomeWinner(PlayerContext context) {
        context.setState(new PlayerWinState());
    }
}
