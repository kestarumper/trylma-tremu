package actors;
import akka.actor.*;

public class PlayerActor extends AbstractActor {

    public static Props props(ActorRef out) {
        return Props.create(PlayerActor.class, out);
    }

    private final ActorRef out;

    public PlayerActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message ->
                        out.tell("I received your message: " + message, self())
                )
                .build();
    }
}