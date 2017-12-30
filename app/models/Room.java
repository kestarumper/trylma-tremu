package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Room {
    public static enum Mode {
        PLAYERS2("Two players", 2),
        PLAYERS4("Four players", 4),
        PLAYERS6("Six players", 6);

        private final String name;
        private final int num;

        Mode(String name, int num) {
            this.name = name;
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public int getNum() {
            return num;
        }
    }
    private String name;
    private User owner;
    private Mode mode;
    private Map<String, User> users;

    public User getOwner() {
        return owner;
    }

    public Room(String name, User owner, Room.Mode mode) {
        this.name = name;
        this.mode = mode;
        this.owner = owner;
        users = new HashMap<>();
    }

    public void joinRoom(User user) {
        if(!users.containsKey(user.getName())) {
            users.put(user.getName(), user);
        }
    }

    public void leaveRoom(User user) {
        if(users.containsKey(user.getName())) {
            users.remove(user.getName());
        }
    }

    public String getMode() {
        return mode.name();
    }

    public String getName() {
        return name;
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
