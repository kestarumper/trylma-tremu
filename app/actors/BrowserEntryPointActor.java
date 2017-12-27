package actors;
import akka.actor.*;
import com.fasterxml.jackson.databind.JsonNode;
import jdk.nashorn.internal.ir.ObjectNode;
import play.libs.Json;

public class BrowserEntryPointActor extends AbstractActor {
    // TODO: Implement BrowserEntryPointActor (javascript client)

    private final ActorRef browser;
    private final ActorRef supervisor;

    public static Props props(ActorRef browser, ActorRef supervisor) {
        return Props.create(BrowserEntryPointActor.class, browser, supervisor);
    }

    public BrowserEntryPointActor(ActorRef browser, ActorRef supervisor) {
        this.browser = browser;
        this.supervisor = supervisor;
        System.out.println(this.browser.toString() + " has started");
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