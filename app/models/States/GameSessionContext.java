package models.States;

public class GameSessionContext {
    protected GameSessionState state;

    public GameSessionContext(){
        this.state = new GamePendingState();
    }

    public void nextState(){
        this.state.moveToNextPhase(this);
    }

    public void setState(GameSessionState newState){
        this.state = newState;
    }

    public GameSessionState getState(){
        return this.state;
    }
}
