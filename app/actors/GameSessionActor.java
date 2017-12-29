package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Builders.JSONBuilder;
import models.GameSession;
import models.Room;
import play.Logger;
import play.libs.Json;

public class GameSessionActor extends AbstractActor {

    private final ActorRef browser;
    private final GameSession gameSession;

    public static Props props(ActorRef browser, GameSession gameSession) {
        return Props.create(GameSessionActor.class, browser, gameSession);
    }

    public GameSessionActor(ActorRef browser, GameSession gameSession) {
        this.browser = browser;
        this.gameSession = gameSession;
        Logger.info("{} started", this.getClass());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {

                    // Otrzymana wiadomosc (String) parsuje na obiekt JsonNode
                    JsonNode jn = Json.parse(message);
                    // Mapowanie JsonNode na obiekt klasy JsonMsg
                    JsonMsg jmsg = Json.fromJson(jn, JsonMsg.class);

                    // TODO: Process Client move request

                    // Send back to client WHOLE Game Session
                    browser.tell(gameSession.getGameBoard().buildMap(new JSONBuilder()), self());
                })
                .build();
    }
}
