package controllers;

import akka.actor.ActorSystem;
import controllers.routes;
import models.TrylmaApp;
import models.User;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final ActorSystem actorSystem;
    private TrylmaApp trylmaApp;

    @Inject
    public HomeController(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
        trylmaApp = TrylmaApp.getInstance();
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        trylmaApp.validateUserSession(session());
        return ok(index.render(session("username")));
    }

    public Result login() {
        Map<String, String[]> map = request().body().asFormUrlEncoded();

        Logger.info("LOGIN {}", map);

        if(map.containsKey("username") && map.containsKey("csrfToken")) {
            String username = map.get("username")[0];
            String csrf = map.get("csrfToken")[0];
            User user = new User(username, csrf);
            if(trylmaApp.getUsers().containsKey(username)) {
                flash("loginerr", "That username is already taken");
            } else {
                Logger.info("PUT {}", user);
                trylmaApp.getUsers().put(username, user);
                session("username", username);
                session("csrf", csrf);
            }
        } else {
            flash("loginerr", "Bad request - does not contain username field.");
        }

        return redirect(controllers.routes.HomeController.index());
    }

    public Result logout() {
        trylmaApp.getUsers().remove(session("username"));
        session().remove("username");
        session().remove("csrf");
        return redirect(controllers.routes.HomeController.index());
    }
}
