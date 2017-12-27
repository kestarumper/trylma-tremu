package controllers;

import actors.RoomDetailActor;
import actors.RoomListActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import controllers.routes;
import models.Room;
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
    private Map<String, Room> rooms = new HashMap<>();

    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public RoomsController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public Result room(String username) {
        if(session("username") == null) {
            return forbidden("You have to be logged to view this page.");
        }
        return ok(room.render(username, rooms.get(username)));
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

            Room room = new Room(roomname, mode);
            room.joinRoom(session("username"));

            rooms.put(session("username"), room);
        } else {
            flash("err", "Bad request - does not contain room fields.");
        }

        return redirect(routes.RoomsController.room(session("username")));
    }

    public WebSocket getRoomDetail(String username) {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef((ActorRef browser) -> RoomDetailActor.props(browser, this.rooms.get(username)),
                        actorSystem, materializer
                )
        );
    }

    public WebSocket getRoomList() {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef((ActorRef browser) -> RoomListActor.props(browser, this.rooms),
                        actorSystem, materializer
                )
        );
    }
}
