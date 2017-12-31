package models;

import akka.actor.ActorRef;
import models.Utility.Point;

import java.util.Objects;

public class User {
    private final String name;
    private final String csrf;
    private ActorRef actorRef;
    private boolean isMoving;
    private String color;
    private Point currentPawn;

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
    }

    public void setPawn(Point newPawn){
        this.currentPawn = newPawn;
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
