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

import java.util.HashMap;
import java.util.Map;

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

    private void tellEveryUserInRoom(Object msg) {
        Map<String, User> users = gameSession.getRoom().getUsers();

        for(User u : users.values()) {
            // do what you have to do here
            u.tell(msg, self());
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {

                        // Otrzymana wiadomosc (String) parsuje na obiekt JsonNode
                        JsonNode jn = Json.parse(message);
                        // Mapowanie JsonNode na obiekt klasy JsonMsg
                        String type = jn.findPath("type").textValue();

                        if(type.equals("move")){
                            Point pointA = new Point(jn.findPath("x1").asInt(), jn.findPath("y1").asInt());
                            Point pointB = new Point(jn.findPath("x2").asInt(), jn.findPath("y2").asInt());
                            if(this.gameSession.getGameBoard().makeAMove(pointA, pointB)){
                                browser.tell("{ \"type\" : \"move\", \"cond\" : true }", self());
                            }
                            else{
                                browser.tell("{ \"type\" : \"move\", \"cond\" : false }", self());
                            }
                        }
                        else {
                            if(type.equals("WebSocketInit")) {
                                String username = jn.findPath("username").asText();
                                gameSession.getRoom().getUsers().get(username).setActorRef(browser);
                                Logger.info("Pairing {} with WebSocket {}", username, browser);
                            }

                            // Send back to client WHOLE Game Session
                            tellEveryUserInRoom(gameSession.getGameBoard().buildMap(new JSONBuilder()));
                        }

                    // TODO: Process Client move request


                })
                .build();
    }
}
