package models;

import akka.actor.ActorRef;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameSession {
    private GameBoard gameBoard;
    private Room room;
    private Queue<User> playerQueue;
    private Queue<String> colors;
    private ActorRef gameSessionActor;
    private ArrayList<User> wonUsers;
    private boolean isGameOver = false;

    public GameSession(GameBoard gameBoard, Room room) {
        this.gameBoard = gameBoard;
        this.room = room;
        this.playerQueue = new LinkedList<>();
        this.colors = gameBoard.getInGameColors();
        this.wonUsers = new ArrayList<>();
    }

    public boolean isGameOver(){
        return this.isGameOver;
    }

    public void setGameSessionActor(ActorRef gameSessionActor) {
        this.gameSessionActor = gameSessionActor;
    }

    public void tell(Object msg, ActorRef sender) {
        gameSessionActor.tell(msg, sender);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ArrayList<User> getWonUsers() {
        return wonUsers;
    }

    public Room getRoom() {
        return room;
    }

    public void passToNext(){
        User tempUser = this.playerQueue.remove();
        tempUser.changeActivity();
        tempUser.setLastMove(null);
        System.out.println("USer: " + tempUser.getName() + " has pawns left: " + tempUser.getPawnsLeft());

        if(!tempUser.isWinner()) {
            this.playerQueue.add(tempUser);
        }
        else{
            if(!this.wonUsers.contains(tempUser)){
                this.wonUsers.add(tempUser);
            }
        }

        if(this.playerQueue.size() == 1){
            tempUser = this.playerQueue.remove();
            this.isGameOver = true;
        }
        if(this.playerQueue.size() > 0) {
            tempUser = this.playerQueue.element();
            tempUser.changeActivity();
        }
    }

    public void addToQueue(User u){
        if(!playerQueue.contains(u)) {
            u.setPawnsNumber(this.gameBoard.getPawnsNumber());
            if (playerQueue.size() == 0) {
                u.changeActivity();
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
