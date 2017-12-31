package models;

import models.GameBoardGenerators.FourPlayerBoard;
import models.GameBoardGenerators.SixPlayerBoard;
import models.GameBoardGenerators.ThreePlayerBoard;
import models.GameBoardGenerators.TwoPlayerBoard;
import models.Strategies.BoardGenerationStrategy;

import java.util.HashMap;
import java.util.Map;

public class Room {
    public static enum Mode {
        PLAYERS2("Two players", 3),
        PLAYERS3("Three players", 3),
        PLAYERS4("Four players", 3),
        PLAYERS6("Six players", 3);

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

        public BoardGenerationStrategy getStrategy(){
            switch(this){
                case PLAYERS2:
                    return new TwoPlayerBoard();
                case PLAYERS4:
                    return new FourPlayerBoard();
                case PLAYERS6:
                    return new SixPlayerBoard();
                case PLAYERS3:
                    return new ThreePlayerBoard();
            }
            return new SixPlayerBoard();
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
