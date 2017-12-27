package controllers;
import actors.PlayerActor;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import akka.actor.*;
import akka.stream.*;
import javax.inject.Inject;

public class WebSocketController extends Controller {
    // TODO: Implement

    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public WebSocketController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        System.out.println("MAIN ACTOR SYSTEM: "+this.actorSystem);
    }

    public WebSocket ws() {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef(PlayerActor::props,
                        actorSystem, materializer
                )
        );
    }
}