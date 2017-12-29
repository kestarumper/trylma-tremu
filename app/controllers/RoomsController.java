package controllers;

import actors.RoomDetailActor;
import actors.RoomListActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import controllers.routes;
import models.GameBoard;
import models.GameBoardGenerators.SixPlayerBoard;
import models.GameSession;
import models.PawnMove.BasicMove;
import models.Room;
import models.TrylmaApp;
import play.Logger;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.newroom;
import views.html.room;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        return ok(room.render(username, trylmaApp.getGameSessions().get(username).getRoom()));
    }

    public Result newRoom() {
        if(session("username") == null) {
            return forbidden("You have to be logged to create new room.");
        }
        return ok(newroom.render(session("username")));
    }

    public Result create() {
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
            }

            Room room = new Room(roomname, session("username"), mode);
            GameBoard gameBoard = new GameBoard(mode.getNum(), new BasicMove(), new SixPlayerBoard());
            GameSession gameSession = new GameSession(gameBoard, room);

            // add creator
            room.joinRoom(session("username"));
            // associate user with it's newly created room
            trylmaApp.getGameSessions().put(session("username"), gameSession);

            Logger.info("{} created new game session for room '{}'", session("username"), room.getName());
        } else {
            flash("err", "Bad request - does not contain room fields.");
        }

        return redirect(routes.RoomsController.room(session("username")));
    }

    public Result joinRoom(String sessionId) {
        // find room and add session user to it
        Room room = trylmaApp.getGameSessions().get(sessionId).getRoom();
        room.joinRoom(session("username"));
        Logger.info("{} joins room {} of {}", session("username"), room.getName(), room.getOwner());
        return redirect(routes.RoomsController.room(sessionId));
    }

    public Result leaveRoom(String sessionId) {
        Room room = trylmaApp.getGameSessions().get(sessionId).getRoom();
        room.leaveRoom(session("username"));
        Logger.info("{} leaves room {} of {}", session("username"), room.getName(), room.getOwner());
        return redirect(routes.RoomsController.room(sessionId));
    }

    public WebSocket getRoomDetail(String sessionId) {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef((ActorRef browser) -> RoomDetailActor.props(browser, trylmaApp.getGameSessions().get(sessionId).getRoom()),
                        actorSystem, materializer
                )
        );
    }

    public WebSocket getRoomList() {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef((ActorRef browser) -> RoomListActor.props(browser, trylmaApp.getGameSessions()),
                        actorSystem, materializer
                )
        );
    }
}
