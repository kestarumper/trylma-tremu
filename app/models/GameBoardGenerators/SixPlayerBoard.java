package models.GameBoardGenerators;

import models.Field.*;
import models.Pawn.*;
import models.Strategies.BoardGenerationStrategy;
import models.Strategies.MoveStrategy;
import models.Utility.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SixPlayerBoard implements BoardGenerationStrategy{

    @Override
    public Field[][] generateGameBoard(int size, int sizeX, int sizeY, MoveStrategy pawnStrategy) {
        Field[][] tempBoard = new Field[sizeX][sizeY];

        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                tempBoard[i][j] = new UnavailableField( new BasicField(new Point(i, j)) );
            }
        }

        ArrayList<Point> tempList = generateBluePoints(size, sizeX);

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new BlueField(new BasicField(p) );
            tempBoard[p.getX()][p.getY()].placePawn(new BluePawn(new BasicPawn(p, pawnStrategy)));
        }

        tempList = generateBlackPoints(size, sizeX);

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new BlackField( new BasicField(p) );
            tempBoard[p.getX()][p.getY()].placePawn(new BlackPawn( new BasicPawn(p, pawnStrategy)));
        }

        tempList = generateGreenPoints(size, sizeX);

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new GreenField( new BasicField(p) );
            tempBoard[p.getX()][p.getY()].placePawn(new GreenPawn( new BasicPawn(p, pawnStrategy)));
        }

        tempList = generateRedPoints(size, sizeX, sizeY);

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new RedField( new BasicField(p) );
            tempBoard[p.getX()][p.getY()].placePawn(new RedPawn( new BasicPawn(p, pawnStrategy)));
        }

        tempList = generateWhitePoints(size);

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new WhiteField( new BasicField(p) );
            tempBoard[p.getX()][p.getY()].placePawn(new WhitePawn( new BasicPawn(p, pawnStrategy)));
        }

        tempList = generateYellowPoints(size);

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new YellowFIeld( new BasicField(p) );
            tempBoard[p.getX()][p.getY()].placePawn(new YellowPawn( new BasicPawn(p, pawnStrategy)));
        }

        tempList = generateNeutralPoints(size);

        for(Point p : tempList){
            tempBoard[p.getX()][p.getY()] = new BasicField(p);
        }

        return tempBoard;
    }

    @Override
    public Queue<String> getColors() {
        Queue<String> tempQueue = new LinkedList<>();
        tempQueue.add("BLE");
        tempQueue.add("GRE");
        tempQueue.add("BCK");
        tempQueue.add("RED");
        tempQueue.add("YEL");
        tempQueue.add("WHT");
        return tempQueue;
    }

    private ArrayList<Point> generateNeutralPoints(int size) {
        ArrayList<Point> tempList = new ArrayList<>();
        int startX = 2 * size;
        int startY = size;

        for(int i = 0; i <= size; i++){
            for(int j = 0; j <= size + i; j++){
                int x = (startX - i) + (2 * j);
                int y = startY + i;
                tempList.add(new Point(x, y));
            }
        }

        startX = size + 1;
        startY = 2 * size + 1;

        for(int i = 0; i < size; i++){
            for(int j = 0; j < (2 * size - i); j++){
                int x = startX + i + j * 2;
                int y = startY + i;
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateBluePoints(int size, int sizeX){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < size; i ++){
            for(int j = 0; j <= i; j++){
                int x = (sizeX / 2) - i + (2 * j);
                int y = i;
                tempList.add(new Point(x, y));
            }
        }
        return tempList;
    }

    private ArrayList<Point> generateBlackPoints(int size, int sizeX){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            for(int j = 0; j <= i; j++){
                int x = sizeX - size - i + 2 * j;
                int y = 2 * size + i + 1;
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateGreenPoints(int size, int sizeX){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size - i; j++){
                int x = sizeX - 2 * size + i + j * 2 + 1;
                int y = size + i;
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateWhitePoints(int size){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            for(int j = 0 ; j < size - i; j++){
                int x = i + j * 2;
                int y = size + i;
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateYellowPoints(int size){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            for(int j = 0; j <= i; j++){
                int x = (size - i - 1) + j * 2;
                int y = size * 2 + i + 1;
                tempList.add(new Point(x, y));
            }
        }

        return tempList;
    }

    private ArrayList<Point> generateRedPoints(int size, int sizeX, int sizeY){
        ArrayList<Point> tempList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size - i; j++){
                int x = (sizeX / 2) - (size - 1) + i + (j * 2);
                int y = sizeY - size + i;
                tempList.add(new Point(x, y));
            }
        }
        return tempList;
    }
}
