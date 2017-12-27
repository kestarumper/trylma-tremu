package models;

import java.util.HashSet;
import java.util.Set;

public class Room {
    public static enum Mode {
        PLAYERS2,
        PLAYERS4,
        PLAYERS6
    }
    private String name;
    private Mode mode;
    private Set<String> users;

    public Room(String name, Mode mode) {
        this.name = name;
        this.mode = mode;
        users = new HashSet<>();
    }

    public void joinRoom(String user) {
        users.add(user);
    }

    public void disconnectFromRoom(String user) {
        users.remove(user);
    }

    public String getMode() {
        return mode.name();
    }

    public String getName() {

        return name;
    }

    public Set<String> getUsers() {
        return users;
    }
}
