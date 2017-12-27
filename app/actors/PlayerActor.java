package actors;
import akka.actor.*;
import com.fasterxml.jackson.databind.JsonNode;
import jdk.nashorn.internal.ir.ObjectNode;
import play.libs.Json;

public class PlayerActor extends AbstractActor {
    // TODO: Implement PlayerActor (javascript client)

    private final ActorRef browser;
    private ActorRef currentGameSession;

    public static Props props(ActorRef browser) {
        return Props.create(PlayerActor.class, browser);
    }

    public PlayerActor(ActorRef browser) {
        this.browser = browser;
        System.out.println(this.browser.toString() + " has started");
    }

    public void setCurrentGameSession(ActorRef currentGameSession) {
        this.currentGameSession = currentGameSession;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    JsonNode jn = Json.parse(message);
                    JsonMsg jmsg = Json.fromJson(jn, JsonMsg.class);
                    browser.tell("I received your message: " + jmsg.value, self());
                })
                .build();
    }
}