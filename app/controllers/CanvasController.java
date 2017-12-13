package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.canvas;

public class CanvasController extends Controller {
    public Result index() {
        return ok(canvas.render("Chinese Checkers", "Mały krok dla Adriana ale wielki dla projektu"));
    }
}
