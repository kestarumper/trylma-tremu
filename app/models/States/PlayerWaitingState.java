package models.States;

public class PlayerWaitingState implements PlayerState {

    @Override
    public void toggleState(PlayerContext context) {
        context.setState(new PlayerMovingState());
    }

    @Override
    public void becomeWinner(PlayerContext context) {
        context.setState(new PlayerWinState());
    }
}
