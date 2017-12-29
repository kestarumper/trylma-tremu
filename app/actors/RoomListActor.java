package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Room;
import play.Logger;
import play.libs.Json;

import java.util.Map;

public class RoomListActor extends AbstractActor {
    private final ActorRef browser;
    private final Map<String, Room> rooms;
    private final ObjectMapper mapper = new ObjectMapper();

    public static Props props(ActorRef browser, Map<String, Room> rooms) {
        return Props.create(RoomListActor.class, browser, rooms);
    }

    public RoomListActor(ActorRef browser, Map<String, Room> rooms) {
        this.browser = browser;
        this.rooms = rooms;
        Logger.info("{} is listing rooms(num: {})", this.getClass(), rooms.size());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    JsonNode jn = Json.parse(message);
                    JsonMsg jmsg = Json.fromJson(jn, JsonMsg.class);

                    browser.tell(mapper.writeValueAsString(rooms), self());
                })
                .build();
    }
}
