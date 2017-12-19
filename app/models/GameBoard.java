package models;

import models.Field.*;
import models.Utility.Point;

import java.util.ArrayList;

public class GameBoard {
    protected Field[][] gameBoardArray;
    protected int sizeOfX, sizeOfY, sizeOfPoints;

    public GameBoard(int size){

        if(size <= 0){
          throw new IllegalArgumentException();
        }

        this.sizeOfX = (6 * size) + 1;
        this.sizeOfY = (4 * size) + 1;
        this.sizeOfPoints = size;
        gameBoardArray = generateGameBoard();

    }

    public String printBoard(){
        StringBuilder board = new StringBuilder();
        for(int i = 0; i < this.sizeOfX; i++){
            for(int j = 0; j < this.sizeOfY; j++){
                board.append(this.gameBoardArray[i][j].getType());
            }
            board.append("\n");
        }

        return board.toString();
    }


    private Field[][] generateGameBoard(){
        Field[][] tempBoard = new Field[this.sizeOfX][this.sizeOfY];

        for(int i = 0; i < this.sizeOfX; i++){
            for(int j = 0; j < this.sizeOfY; j++){
                tempBoard[i][j] = new UnavailableField();
            }
        }

        ArrayList<Point> tempList = generateBluePoints();

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new BlueField();
        }

        tempList = generateBlackPoints();

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new BlackField();
        }

        tempList = generateGreenPoints();

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new GreenField();
        }

        tempList = generateRedPoints();

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new RedField();
        }

        tempList = generateWhitePoints();

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new WhiteField();
        }

        tempList = generateYellowPoints();

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new YellowFIeld();
        }

        return tempBoard;
    }

    private ArrayList<Point> generateBluePoints(){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < this.sizeOfPoints; i ++){
            for(int j = 0; j <= i; j++){
                int x = this.sizeOfX / 2 - (i) + (2 * j);
                int y = i;
                tempList.add(new Point(x, y));
            }
        }
        return tempList;
    }

    private ArrayList<Point> generateBlackPoints(){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < this.sizeOfPoints; i++){
            for(int j = 0; j <= i; j++){
                int x = (2 * this.sizeOfPoints) + i;
                int y = (this.sizeOfY - 1) - i + (2 * j);
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateGreenPoints(){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < this.sizeOfPoints; i++){
            for(int j = 0; j < this.sizeOfPoints - i; j++){
                int x = this.sizeOfX - this.sizeOfPoints * 2 + 2 * j;
                int y = this.sizeOfPoints + i;
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateWhitePoints(){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < this.sizeOfPoints; i++){
            for(int j = 0 ; j < this.sizeOfPoints - i; j++){
                int x = 0 + j * 2;
                int y = this.sizeOfPoints + i;
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateYellowPoints(){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < this.sizeOfPoints; i++){
            for(int j = 0; j <= i; j++){
                int x = (this.sizeOfPoints * 2) + i;
                int y = 0 + j * 2;
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateRedPoints(){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < this.sizeOfPoints; i++){
            for(int j = 0; j < this.sizeOfPoints - i; j++){
                int x = (this.sizeOfX / 2) - this.sizeOfPoints + (j * 2);
                int y = this.sizeOfY - this.sizeOfPoints + i;
                tempList.add(new Point(x, y));
            }
        }
        return tempList;
    }
}
