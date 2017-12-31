package models.Strategies;

import models.Field.Field;

import java.util.Queue;

public interface BoardGenerationStrategy {
    public Field[][] generateGameBoard(int size, int sizeX, int sizeY, MoveStrategy pawnStrategy);
    public Queue<String> getColors();

}
