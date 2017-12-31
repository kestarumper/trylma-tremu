package models;

import java.util.LinkedList;
import java.util.Queue;

public class GameSession {
    private GameBoard gameBoard;
    private Room room;
    private Queue<User> playerQueue;
    private Queue<String> colors;

    public GameSession(GameBoard gameBoard, Room room) {
        this.gameBoard = gameBoard;
        this.room = room;
        this.playerQueue = new LinkedList<>();
        this.colors = gameBoard.getInGameColors();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Room getRoom() {
        return room;
    }

    public void passToNext(){
        User tempUser = this.playerQueue.remove();
        tempUser.setActivity(false);
        this.playerQueue.add(tempUser);

        tempUser = this.playerQueue.element();
        tempUser.setActivity(true);
    }

    public void addToQueue(User u){
        if(!playerQueue.contains(u)) {
            if (playerQueue.size() == 0) {
                u.setActivity(true);
                u.setColor(this.colors.remove());
                playerQueue.add(u);
            } else {
                u.setColor(this.colors.remove());
                playerQueue.add(u);
            }
            System.out.println("Added user: " + u.getName() + " to room: " + room.getName());
        }
    }
}
