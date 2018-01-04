package models;

import akka.actor.ActorRef;
import models.States.PlayerContext;
import models.States.PlayerMovingState;
import models.States.PlayerWinState;
import models.Utility.Point;

import java.util.Objects;

public class User {
    protected final String name;
    protected final String csrf;
    protected ActorRef actorRef;
    protected PlayerContext stateContext;
    protected String color;
    protected Point currentPawn;
    protected Point lastMove;
    protected int pawnsLeft;

    public User(String name, String csrf) {
        this.name = name;
        this.csrf = csrf;
        this.actorRef = null;
        this.stateContext = new PlayerContext();
    }

    public String getName() {
        return name;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

    public void setActorRef(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    public ActorRef getActorRef(){
        return this.actorRef;
    }

    public void tell(Object msg, ActorRef sender) {
        if(actorRef != null) {
            actorRef.tell(msg, sender);
        }
    }

    public void setPawnsNumber(int number){
        this.pawnsLeft = number;
    }

    public void decrementPawns(){
        this.pawnsLeft -= 1;
    }

    public int getPawnsLeft(){
        return this.pawnsLeft;
    }

    public void changeActivity(){
        this.stateContext.toggleFocus();
        this.currentPawn = null;
        this.lastMove = null;
    }

    public boolean getActivity(){
        if(this.stateContext.getState() instanceof PlayerMovingState){
            return true;
        }
        return false;
    }

    public void setAsWinner(){
        this.stateContext.setAsWinner();
    }

    public boolean isWinner(){
        if(this.stateContext.getState() instanceof PlayerWinState){
            return true;
        }

        return false;
    }

    public void setPawn(Point newPawn){
        this.currentPawn = newPawn;
    }
    public Point getCurrentPawn(){
        return this.currentPawn;
    }

    public boolean isTheSamePawn(Point newPawn){
        if(this.currentPawn != null) {
            if (this.currentPawn.getX() == newPawn.getX() && this.currentPawn.getY() == newPawn.getY()) {
                return true;
            }
        }
        else{
            return true;
        }
        return false;
    }

    public void setLastMove(Point move){
        this.lastMove = move;
    }

    public Point getLastMove(){
        return this.lastMove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
