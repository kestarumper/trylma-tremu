package models;

import models.Builders.Builder;
import models.Field.Field;
import models.Pawn.Pawn;
import models.Strategies.BoardGenerationStrategy;
import models.Strategies.MoveStrategy;
import models.Utility.Point;

import java.util.Queue;

public class GameBoard {
    protected Field[][] gameBoardArray;
    protected int sizeOfX, sizeOfY, sizeOfPoints;
    protected Queue<String> colors;

    //Strategies for the board
    protected MoveStrategy pawnStrategy;

    public GameBoard(int size, MoveStrategy pawnStrategy, BoardGenerationStrategy boardGenerator){

        if(size <= 0){
          throw new IllegalArgumentException();
        }

        this.pawnStrategy = pawnStrategy;



        this.sizeOfX = (6 * size) + 1;
        this.sizeOfY = (4 * size) + 1;
        this.sizeOfPoints = size;
        gameBoardArray = boardGenerator
                .generateGameBoard(this.sizeOfPoints, this.sizeOfX, this.sizeOfY, this.pawnStrategy);
        this.colors = boardGenerator.getColors();

    }

    public Queue<String> getInGameColors(){
        return this.colors;
    }

    public boolean makeAMove(Point start, Point end, String playerColor){
        Pawn tempPawn = this.gameBoardArray[start.getX()][start.getY()].getPawn();

        if(tempPawn != null) {
            if(playerColor.equals(tempPawn.getColor())) {
                return tempPawn.makeMove(end, this.gameBoardArray);
            }
        }

        return false;
    }

    public String printBoard(){
        StringBuilder board = new StringBuilder();
        for(int i = 0; i < this.sizeOfY; i++){
            for(int j = 0; j < this.sizeOfX; j++){
                if(this.gameBoardArray[j][i].getType().equals("")){
                    board.append("███");
                }
                else if(this.gameBoardArray[j][i].getType().equals("UNV")){
                    board.append("___");
                }
                else {
                    board.append(this.gameBoardArray[j][i].getType());
                }
            }
            board.append("\n");
        }

        return board.toString();
    }

    public String buildMap(Builder concreteBuilder){
        concreteBuilder.setType("map");

        for(int x = 0; x < this.sizeOfX; x++){
            for(int y = 0; y < this.sizeOfY; y++){

                if(!gameBoardArray[x][y].getType().equals("UNV")) {
                    concreteBuilder.addField(x, y, gameBoardArray[x][y].getType());
                }

                if(gameBoardArray[x][y].getPawn() != null){
                    concreteBuilder.addPawn(x, y, gameBoardArray[x][y].getPawn().getColor());
                }
            }
        }

        return concreteBuilder.getResult();
    }
    
    public String getField(int x, int y){
        if(x < 0 || y < 0){
            throw new IllegalArgumentException();
        }

        if( x >= this.sizeOfX || y >= this.sizeOfY){
            throw new IllegalArgumentException();
        }

        return gameBoardArray[x][y].getType();
    }

}
