package models.Builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.GameBoard;
import models.GameBoardGenerators.ThreePlayerBoard;
import models.PawnMove.BasicMove;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class BuilderTest {

    Builder testBuilder;

    @Test
    public void shouldPrintJSON(){
        GameBoard testBoard = new GameBoard(2, new BasicMove(), new ThreePlayerBoard());
        String test = testBoard.buildMap(new JSONBuilder());

        assertTrue(isJSONValid(test));
    }

    public static boolean isJSONValid(String jsonInString ) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}