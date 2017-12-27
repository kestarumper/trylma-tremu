package controllers;

import controllers.routes;
import play.data.DynamicForm;
import play.mvc.*;

import views.html.*;

import java.util.Map;
import java.util.Set;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

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
            session("username", map.get("username")[0]);
        }

        return redirect(controllers.routes.HomeController.index());
    }

    public Result logout() {
        session().remove("username");
        return redirect(controllers.routes.HomeController.index());
    }
}
