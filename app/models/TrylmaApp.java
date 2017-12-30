package models;

import play.Logger;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Map;

// Singleton
public class TrylmaApp {
    private static volatile TrylmaApp instance;

    private Map<String, GameSession> gameSessions;

    private Map<String, User> users;

    public static TrylmaApp getInstance() {
        if (instance == null) { // first time lock
            synchronized (TrylmaApp.class) {
                if (instance == null) {  // second time lock
                    instance = new TrylmaApp();
                }
            }
        }
        return instance;
    }

    private TrylmaApp() {
        gameSessions = new HashMap<>();
        users = new HashMap<>();
        Logger.info("Creating SINGLETON TrylmaApp");
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void validateUserSession(Http.Session s) {
        Logger.info("Session for user: {} - {}", s.get("username"), s.get("csrf"));

        if(users.containsKey(s.get("username"))) {
            // check if saved in hashmap is the same
            if(!users.get(s.get("username")).getCsrf().equals(s.get("csrf"))) {
                Logger.error("CSRF Mismatch({}) - {} != {}", s.get("username"), users.get(s.get("username")).getCsrf(), s.get("csrf"));
                s.remove("username");
                s.remove("csrf");
            }
        } else if(s.get("username") != null){
            Logger.error("User {} not found in server", s.get("username"));
            s.remove("username");
            s.remove("csrf");
        }
    }

    public Map<String, GameSession> getGameSessions() {
        return gameSessions;
    }
}
