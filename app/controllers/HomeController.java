package controllers;

import controllers.routes;
import play.data.DynamicForm;
import play.mvc.*;

import views.html.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    static private Set<String> used_usernames = new HashSet<>();

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render(session("username")));
    }

    public Result login() {
        Map<String, String[]> map = request().body().asFormUrlEncoded();

        if(map.containsKey("username")) {
            String usr = map.get("username")[0];
            if(used_usernames.contains(usr)) {
                flash("loginerr", "That username is already taken");
            } else {
                used_usernames.add(usr);
                session("username", usr);
            }
        } else {
            flash("loginerr", "Bad request - does not contain username field.");
        }

        return redirect(controllers.routes.HomeController.index());
    }

    public Result logout() {
        used_usernames.remove(session("username"));
        session().remove("username");
        return redirect(controllers.routes.HomeController.index());
    }
}
