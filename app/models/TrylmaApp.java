package models;

import play.Logger;

import java.util.HashMap;
import java.util.Map;

// Singleton
public class TrylmaApp {
    private static volatile TrylmaApp instance;

    private Map<String, Room> rooms;

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

    public Map<String, Room> getRooms() {
        return rooms;
    }
}
