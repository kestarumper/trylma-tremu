package models;

public class GameSession {
    private GameBoard gameBoard;
    private Room room;

    public GameSession(GameBoard gameBoard, Room room) {
        this.gameBoard = gameBoard;
        this.room = room;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Room getRoom() {
        return room;
    }
}
