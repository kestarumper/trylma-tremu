package controllers;
import actors.BrowserEntryPointActor;
import actors.GameSessionActor;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import akka.actor.*;
import akka.stream.*;
import javax.inject.Inject;

public class WebSocketController extends Controller {
    // TODO: Implement

    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final ActorRef gameSessionSupervisorActor;

    @Inject
    public WebSocketController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.gameSessionSupervisorActor = this.actorSystem.actorOf(GameSessionActor.props(), "GameSessionSupervisor");
    }

    public WebSocket ws() {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef((ActorRef browser) -> BrowserEntryPointActor.props(browser, this.gameSessionSupervisorActor),
                        actorSystem, materializer
                )
        );
    }
}