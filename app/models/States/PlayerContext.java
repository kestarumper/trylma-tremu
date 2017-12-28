package models.States;

public class PlayerContext {
    private PlayerState state;

    public PlayerContext(){
        this.state = new PlayerWaitingState();
    }

    public void toggleFocus(){
        this.state.toggleState(this);
    }

    public void setAsWinner(){
        this.state.becomeWinner(this);
    }

    public void setState(PlayerState newState){
        this.state = newState;
    }
    public PlayerState getState(){
        return this.state;
    }
}
