package controllers;

import actors.GameSessionActor;
import actors.RoomListActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import models.Builders.JSONBuilder;
import models.GameBoard;
import models.GameBoardGenerators.FourPlayerBoard;
import models.GameBoardGenerators.SixPlayerBoard;
import models.PawnMove.BasicMove;
import models.TrylmaApp;
import play.Logger;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Result;

import play.mvc.WebSocket;
import views.html.board;

import javax.inject.Inject;

public class BoardDrawController extends Controller {
    private TrylmaApp trylmaApp;

    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public BoardDrawController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        trylmaApp = TrylmaApp.getInstance();
        Logger.info("BoardDrawController instance created");
    }

    public Result index(String sessionId){
        return ok(board.render(sessionId, trylmaApp.getGameSessions().get(sessionId).getRoom()));
    }

    public WebSocket board(String sessionId) {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef((ActorRef browser) -> GameSessionActor.props(browser, trylmaApp.getGameSessions().get(sessionId)),
                        actorSystem, materializer
                )
        );
    }
}