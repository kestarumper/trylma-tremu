package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class UserActor extends AbstractActor {

    private String username;

    public UserActor(String username) {
        this.username = username;
    }

    public static Props props(String username) {
        return Props.create(UserActor.class, username);
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
