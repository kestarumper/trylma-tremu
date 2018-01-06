package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.BasicBot;
import models.GameSession;
import play.Logger;
import play.libs.Json;


/**
 * Similar to {@link BrowserEntryPointActor}, except it acts as
 * an emulated version of browser JavaScript.
 * Simulates user's inputs and processes {@link GameSessionActor} outputs.
 */
public class VirtualBrowserActor extends AbstractActor {
    private final GameSession gameSession;
    private final BasicBot bot;
    private final ObjectMapper mapper = new ObjectMapper();

    public static Props props(GameSession gameSession, BasicBot bot) {
        return Props.create(VirtualBrowserActor.class, gameSession, bot);
    }

    public VirtualBrowserActor(GameSession gameSession, BasicBot bot) {
        this.gameSession = gameSession;
        this.bot = bot;
        Logger.info("{} started", this.getClass().getSimpleName());

        gameSession.tell("{\"type\":\"WebSocketInit\", \"username\":\""+ bot.getName() +"\"}", self());
        Logger.info("{} entered game \"{}\" and sent WebSocketInit", bot.getName(), gameSession.getRoom().getName());
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        Logger.info("{} dies", bot.getName());
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        Logger.info("{} starts", bot.getName());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    JsonNode jn = Json.parse(message);
                    JsonMsg jmsg = Json.fromJson(jn, JsonMsg.class);

                    Logger.info("{} received: {}", bot.getName(), message);

                    if(jmsg.type.equals("refresh") || jmsg.type.equals("color")) {
                        if(bot.getActivity()) {
                            sender().tell(bot.action(gameSession.getGameBoard()), self());
                            sender().tell(bot.pass(), self());
                        }
                        if(gameSession.isGameOver()){
                            gameSession.getRoom().leaveRoom(bot);
                        }
                    }

                    if(jmsg.type.equals("move")) {
                        if(jn.findPath("cond").asBoolean()) {
                            sender().tell(bot.pass(), self());
                        } else {
                            sender().tell(bot.action(gameSession.getGameBoard()), self());
                        }
                    }
                })
                .build();
    }
}
