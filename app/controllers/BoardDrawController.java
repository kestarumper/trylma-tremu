package controllers;

import models.Builders.JSONBuilder;
import models.GameBoard;
import models.GameBoardGenerators.FourPlayerBoard;
import models.GameBoardGenerators.SixPlayerBoard;
import models.PawnMove.BasicMove;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.board;

public class BoardDrawController extends Controller {
    public Result index(){
        return ok(board.render("Board Draw test", "Testowanie rysowania planszy"));
    }

    public Result board(){
        GameBoard testBoard = new GameBoard(4, new BasicMove(), new SixPlayerBoard());
        return ok(testBoard.buildMap(new JSONBuilder()));
    }
}
