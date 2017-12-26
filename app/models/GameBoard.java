package models;

import models.Field.*;
import models.Pawn.*;
import models.Strategies.BoardGenerationStrategy;
import models.Strategies.MoveStrategy;
import models.Utility.Point;

import java.util.ArrayList;

public class GameBoard {
    protected Field[][] gameBoardArray;
    protected int sizeOfX, sizeOfY, sizeOfPoints;

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
