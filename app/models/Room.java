package models;

import akka.actor.ActorRef;
import models.GameBoardGenerators.FourPlayerBoard;
import models.GameBoardGenerators.SixPlayerBoard;
import models.GameBoardGenerators.ThreePlayerBoard;
import models.GameBoardGenerators.TwoPlayerBoard;
import models.Strategies.BoardGenerationStrategy;

import java.util.HashMap;
import java.util.Map;

public class Room {
    public static enum Mode {
        PLAYERS2("Two players", 2),
        PLAYERS3("Three players", 4),
        PLAYERS4("Four players", 4),
        PLAYERS6("Six players", 4);

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
        public int getMaxPlayers(){
            switch(this){
                case PLAYERS2:
                    return 2;
                case PLAYERS4:
                    return 4;
                case PLAYERS6:
                    return 6;
                case PLAYERS3:
                    return 3;
            }
            return 6;
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


    /**
     * Adds {@link User} to certain {@link Room}.
     * User is added by putting him in {@link HashMap}
     * @param user
     */
    public void joinRoom(User user) {
        if(!users.containsKey(user.getName())) {
            users.put(user.getName(), user);
        }
    }

    public int getMaxUsers(){
        return mode.getMaxPlayers();
    }

    /**
     * Adds {@link User} to certain {@link Room}.
     * User is added by putting him in {@link HashMap}
     * @param user
     */
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


    /**
     * Sends message msg to every {@link User} in {@link Room}
     * which are handled by Actors
     * @param msg
     * @param self
     * @see ActorRef
     */
    public void tell(Object msg, ActorRef self) {
        for(User u : users.values()) {
            // do what you have to do here
            u.tell(msg, self);
        }
    }

    public void update(Object msg, ActorRef self, User demandingUser){
        for(User u : users.values()){
            if(!u.getName().equals(demandingUser.getName())){
               u.tell(msg, self);
            }
        }
    }
}
