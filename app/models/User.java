package models;

import akka.actor.ActorRef;
import models.Utility.Point;

import java.util.Objects;

public class User {
    protected final String name;
    protected final String csrf;
    protected ActorRef actorRef;
    protected boolean isMoving;
    protected String color;
    protected Point currentPawn;
    protected Point lastMove;

    public User(String name, String csrf) {
        this.name = name;
        this.csrf = csrf;
        this.actorRef = null;
        this.isMoving = false;
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

    public void tell(Object msg, ActorRef sender) {
        if(actorRef != null) {
            actorRef.tell(msg, sender);
        }
    }

    public void setActivity(boolean activity){
        this.isMoving = activity;
        this.currentPawn = null;
        this.lastMove = null;
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

    public boolean getActivity(){
        return this.isMoving;
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
