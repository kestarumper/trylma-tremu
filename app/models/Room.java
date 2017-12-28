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
    private String owner;
    private Mode mode;
    private Set<String> users;

    public String getOwner() {
        return owner;
    }

    public Room(String name, String owner, Room.Mode mode) {
        this.name = name;
        this.mode = mode;
        this.owner = owner;

        users = new HashSet<>();
    }

    public void joinRoom(String user) {
        if(!users.contains(user)) {
            users.add(user);
        }
    }

    public void leaveRoom(String user) {
        if(users.contains(user)) {
            users.remove(user);
        }
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
