package models.States;

public interface PlayerState {
    public void toggleState(PlayerContext context);
    public void becomeWinner(PlayerContext context);
}
