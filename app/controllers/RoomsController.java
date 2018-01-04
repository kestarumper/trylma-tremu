package controllers;

import actors.GameSessionActor;
import actors.RoomDetailActor;
import actors.RoomListActor;
import actors.VirtualBrowserActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import models.*;
import models.PawnMove.BasicMove;
import play.Logger;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.newroom;
import views.html.room;

import javax.inject.Inject;
import java.util.Map;

public class RoomsController extends Controller {
    private TrylmaApp trylmaApp;

    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public RoomsController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        trylmaApp = TrylmaApp.getInstance();
        Logger.info("RoomsController instance created");
    }

    public Result room(String username) {
        if(session("username") == null) {
            return forbidden("You have to be logged to view this page.");
        }
        trylmaApp.validateUserSession(session());

        if(trylmaApp.getGameSessions().get(username) == null) {
            return notFound("Room your are accessing does not exist");
        }

        return ok(room.render(username, trylmaApp.getGameSessions().get(username).getRoom()));
    }

    public Result newRoom() {
        if(session("username") == null) {
            return forbidden("You have to be logged to create new room.");
        }
        trylmaApp.validateUserSession(session());
        return ok(newroom.render(session("username")));
    }

    public Result create() {
        if(session("username") == null) {
            return forbidden("You have to be logged to create new room.");
        }
        trylmaApp.validateUserSession(session());

        Map<String, String[]> map = request().body().asFormUrlEncoded();

        if(map.containsKey("roomname") && map.containsKey("gamemode")) {
            String roomname = map.get("roomname")[0];
            String gamemode = map.get("gamemode")[0];

            Room.Mode mode = Room.Mode.PLAYERS2;

            switch (gamemode) {
                case "2-Players":
                    mode = Room.Mode.PLAYERS2;
                    break;
                case "4-Players":
                    mode = Room.Mode.PLAYERS4;
                    break;
                case "6-Players":
                    mode = Room.Mode.PLAYERS6;
                    break;
                case "3-Players":
                    mode = Room.Mode.PLAYERS3;
                    break;
            }

            User user = trylmaApp.getUsers().get(session("username"));
            Room room = new Room(roomname, user, mode);

            GameBoard gameBoard = new GameBoard(mode.getNum(), new BasicMove(), mode.getStrategy());
            GameSession gameSession = new GameSession(gameBoard, room);
            ActorRef gameSessionActor = actorSystem.actorOf(GameSessionActor.props(gameSession));
            gameSession.setGameSessionActor(gameSessionActor);

            // add creator
            room.joinRoom(user);
            // associate user with it's newly created room
            trylmaApp.getGameSessions().put(session("username"), gameSession);

            Logger.info("{} created new game session for room '{}'", session("username"), room.getName());
        } else {
            flash("err", "Bad request - does not contain room fields.");
        }

        return redirect(routes.RoomsController.room(session("username")));
    }

    public Result joinRoom(String sessionId) {
        if(session("username") == null) {
            return forbidden("You have to be logged to create new room.");
        }
        trylmaApp.validateUserSession(session());
        // find room and add session user to it
        User user = trylmaApp.getUsers().get(session("username"));
        Room room = trylmaApp.getGameSessions().get(sessionId).getRoom();
        if(room.getUsers().size() < room.getMaxUsers()) {
            room.joinRoom(user);
        }
        else{
            //TODO: Tell user that room is full
            flash("joinerr", "Room is full");
        }
        Logger.info("{} joins room {} of {}", session("username"), room.getName(), room.getOwner());
        return redirect(routes.RoomsController.room(sessionId));
    }

    public Result leaveRoom(String sessionId) {
        if(session("username") == null) {
            return forbidden("You have to be logged to create new room.");
        }
        trylmaApp.validateUserSession(session());

        User user = trylmaApp.getUsers().get(session("username"));
        Room room = trylmaApp.getGameSessions().get(sessionId).getRoom();
        room.leaveRoom(user);

        // destroy game session if owner left
        if(!room.getUsers().containsKey(room.getOwner().getName())) {
            trylmaApp.destroyGameSession(room.getOwner());
        }

        Logger.info("{} leaves room {} of {}", session("username"), room.getName(), room.getOwner());
        return redirect(routes.RoomsController.room(sessionId));
    }

    public Result addBot(String sessionId) {
        if(session("username") == null) {
            return forbidden("You have to be logged to create new bot.");
        }
        trylmaApp.validateUserSession(session());
        // find room and add session user to it
        User user = trylmaApp.getUsers().get(session("username"));

        GameSession gameSession = trylmaApp.getGameSessions().get(sessionId);

        BasicBot bot = new BasicBot(user.getName(), user.getCsrf(), gameSession.getGameBoard());
        Room room = gameSession.getRoom();

        // create virtual browser that will resemble normal user
        ActorRef virtualBrowser = actorSystem.actorOf(VirtualBrowserActor.props(gameSession, bot));
        bot.setActorRef(virtualBrowser);
        //TODO: Add option to initialize alle players into quque whene game starts
        //gameSession.addToQueue(bot);

        room.joinRoom(bot);

        Logger.info("{} joins room {} of {}", bot.getName(), room.getName(), room.getOwner().getName());
        return redirect(routes.RoomsController.room(sessionId));
    }

    public WebSocket getRoomDetail(String sessionId) {
        if(session("username") == null) {
            return null;
        }
        trylmaApp.validateUserSession(session());

        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef((ActorRef browser) -> RoomDetailActor.props(browser, trylmaApp.getGameSessions().get(sessionId).getRoom()),
                        actorSystem, materializer
                )
        );
    }

    public WebSocket getRoomList() {
        if(session("username") == null) {
            return null;
        }
        trylmaApp.validateUserSession(session());
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef((ActorRef browser) -> RoomListActor.props(browser, trylmaApp.getGameSessions()),
                        actorSystem, materializer
                )
        );
    }
}
