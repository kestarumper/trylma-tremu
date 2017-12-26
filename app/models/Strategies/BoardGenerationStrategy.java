package models.Strategies;

import models.Field.Field;

public interface BoardGenerationStrategy {
    public Field[][] generateGameBoard(int size, int sizeX, int sizeY, MoveStrategy pawnStrategy);
}
