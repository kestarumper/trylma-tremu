package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import models.Builders.JSONBuilder;
import models.GameSession;
import models.User;
import models.Utility.Point;
import play.Logger;
import play.libs.Json;


/**
 * Acts as a pipe. Connects one end of the "browser, a client side"
 * with the other end of {@link GameSessionActor} that interprets moves.
 */
public class BrowserEntryPointActor extends AbstractActor {
    private ActorRef browser;
    private final GameSession gameSession;

    public static Props props(ActorRef browser, GameSession gameSession) {
        return Props.create(BrowserEntryPointActor.class, browser, gameSession);
    }

    public BrowserEntryPointActor(ActorRef browser, GameSession gameSession) {
        this.browser = browser;
        this.gameSession = gameSession;
        Logger.info("{} started", this.getClass());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    gameSession.tell(message, browser);
                })
                .build();
    }
}
