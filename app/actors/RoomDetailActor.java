package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Room;
import play.Logger;
import play.libs.Json;

public class RoomDetailActor extends AbstractActor {
    private final ActorRef browser;
    private final Room room;

    public static Props props(ActorRef browser, Room room) {
        return Props.create(RoomDetailActor.class, browser, room);
    }

    public RoomDetailActor(ActorRef browser, Room room) {
        this.browser = browser;
        this.room = room;
        Logger.info("{} for room: {}[{}]", this.getClass(), room.getName(), room.getMode());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    JsonNode jn = Json.parse(message);
                    JsonMsg jmsg = Json.fromJson(jn, JsonMsg.class);

                    ObjectMapper mapper = new ObjectMapper();

                    browser.tell( mapper.writeValueAsString(room), self());
                })
                .build();
    }
}
