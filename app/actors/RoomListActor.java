package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.GameSession;
import models.Room;
import play.Logger;
import play.libs.Json;

import java.util.Map;

public class RoomListActor extends AbstractActor {
    private final ActorRef browser;
    private final Map<String, GameSession> gameSessions;
    private final ObjectMapper mapper = new ObjectMapper();

    public static Props props(ActorRef browser, Map<String, GameSession> gameSessions) {
        return Props.create(RoomListActor.class, browser, gameSessions);
    }

    public RoomListActor(ActorRef browser, Map<String, GameSession> gameSessions) {
        this.browser = browser;
        this.gameSessions = gameSessions;
        Logger.info("{} is listing rooms(num: {})", this.getClass(), gameSessions.size());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    JsonNode jn = Json.parse(message);
                    JsonMsg jmsg = Json.fromJson(jn, JsonMsg.class);

                    browser.tell(mapper.writeValueAsString(gameSessions), self());
                })
                .build();
    }
}
