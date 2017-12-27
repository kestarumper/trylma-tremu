package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class GameSessionActor extends AbstractActor {
    // TODO: Implement

    public static Props props(ActorRef out) {
        return Props.create(GameSessionActor.class, out);
    }

    private final ActorRef out;

    public GameSessionActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    getSender().tell("Oki doki", getSelf());
                })
                .build();
    }
}
