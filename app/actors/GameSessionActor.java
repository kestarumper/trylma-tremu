package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import models.Builders.JSONBuilder;
import models.GameSession;
import models.Utility.Point;
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
                        else{
                            // Send back to client WHOLE Game Session
                            browser.tell(gameSession.getGameBoard().buildMap(new JSONBuilder()), self());
                        }

                    // TODO: Process Client move request


                })
                .build();
    }
}
