package models;

import play.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Singleton
public class TrylmaApp {
    private static volatile TrylmaApp instance;

    private Map<String, Room> rooms;

    private Set<String> users = new HashSet<>();

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
        rooms = new HashMap<>();
        Logger.info("Creating SINGLETON TrylmaApp");
    }

    public Set<String> getUsers() {
        return users;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }
}
