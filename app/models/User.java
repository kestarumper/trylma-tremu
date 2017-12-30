package models;

import akka.actor.ActorRef;

import java.util.Objects;

public class User {
    private final String name;
    private final String csrf;
    private ActorRef actorRef;

    public User(String name, String csrf) {
        this.name = name;
        this.csrf = csrf;
        this.actorRef = null;
    }

    public String getName() {
        return name;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setActorRef(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    public void tell(Object msg, ActorRef sender) {
        actorRef.tell(msg, sender);
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
