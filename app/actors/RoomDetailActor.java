package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.routes;
import models.GameSession;
import models.Room;
import models.User;
import play.Logger;
import play.libs.Json;

public class RoomDetailActor extends AbstractActor {
    private final ActorRef browser;
    private final GameSession gameSession;

    public static Props props(ActorRef browser, GameSession gameSession) {
        return Props.create(RoomDetailActor.class, browser, gameSession);
    }

    public RoomDetailActor(ActorRef browser, GameSession gameSession) {
        this.browser = browser;
        this.gameSession = gameSession;
        Logger.info("{} for room: {}[{}]", this.getClass(), gameSession.getRoom().getName(), gameSession.getRoom().getMode());
    }

    private void redirectUsersTo(String url) {
        Logger.info("Redirecting users in room {} to {}", gameSession.getRoom().getName(), url);
//        for(User u : gameSession.getRoom().getUsers().values()) {
//            u.tell("{\"type\":\"redirect\", \"url\":\""+ url +"\"}", self());
//        }
        browser.tell("{\"type\":\"redirect\", \"url\":\""+ url +"\"}", self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    JsonNode jn = Json.parse(message);
                    JsonMsg jmsg = Json.fromJson(jn, JsonMsg.class);

                    ObjectMapper mapper = new ObjectMapper();

                    if(gameSession.isGameStarted() && !gameSession.isGameOver()) {
                        redirectUsersTo(controllers.routes.BoardDrawController.index(gameSession.getRoom().getOwner().getName()).url());
                    }

                    browser.tell( mapper.writeValueAsString(gameSession.getRoom()), self());
                })
                .build();
    }
}
