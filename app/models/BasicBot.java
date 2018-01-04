package models;

import models.Field.Field;
import models.Pawn.Pawn;
import models.Utility.Point;

import java.util.ArrayList;

public class BasicBot extends User {
    private final GameBoard gameBoard;
    private boolean initialized;
    private ArrayList<Pawn> botPawns;
    private ArrayList<Field> fieldsToAcheive;

    public BasicBot(String name, String csrf, GameBoard gameBoard) {
        super(name.concat("Bot"), csrf);
        this.gameBoard = gameBoard;
        this.initialized = false;
    }

    private void initializeBot(){
        this.botPawns = calculateSelfPawns();
        this.fieldsToAcheive = calculateFieldsToAcheive();
    }

    private Pawn getReadyPawn() {
        return botPawns.get((int)Math.round(Math.random()*botPawns.size()));
    }


    /**
     * Generates {@link ArrayList<Field>} containing
     * possible moves ({@link Field}s to which {@link Pawn} can be moved)
     * @param pawn {@link Pawn} around who we are calculating
     * @return {@link ArrayList<Field>} of possible moves
     */
    private ArrayList<Field> calculateAvailableMoves(Pawn pawn) {
        ArrayList<Field> result = new ArrayList<>();

        int x = pawn.getPosition().getX();
        int y = pawn.getPosition().getY();
        Point point = new Point(x, y);

        for(int i = -2; i <= 2; i++) {
            // continue if bad position
            if(x+i < 0 || x+i >= gameBoard.getSizeOfX()) {
                continue;
            }

            for(int j = -2; j <= 2; j++) {
                // continue if bad position
                if(y+j < 0 || y+j >= gameBoard.getSizeOfY()) {
                    continue;
                }

                point.setX(x+i);
                point.setY(y+j);

                if(pawn.checkMove(point, gameBoard.getGameBoardArray(), this)) {
                    result.add(gameBoard.getField(point.getX(), point.getY()));
                }
            }
        }

        return result;
    }


    /**
     * Returns distance between two fields.
     * @param candidate
     * @param destination
     * @return Cartesian distance
     */
    private double calculateDistance(Field candidate, Field destination) {
        return Math.sqrt(
               Math.pow(destination.getPosition().getX() - candidate.getPosition().getX(), 2) +
               Math.pow(destination.getPosition().getY() - candidate.getPosition().getY(), 2)
        );
    }

    private double calculateBestDistance(Field start, ArrayList<Field> destFields) {
        // TODO: tudududududu wonsz
        return 0;
    }

    private Field getMoveDesicion(ArrayList<Field> availableMoves, ArrayList<Field> destinationFields){
        //TODO: oblicz dystans dla kazdego i zwroc najlepszego
        double min = -1;
        Field best = null;

        for (Field availableField : availableMoves) {
            double fieldResult = calculateBestDistance(availableField, destinationFields);
            if(fieldResult < min || min == -1) {
                min = fieldResult;
                best = availableField;
            }
        }

        return best;
    }

    private ArrayList<Pawn> calculateSelfPawns(){
        ArrayList<Pawn> tempArray = new ArrayList<>();

        String ourColor = this.color;
        int x = this.gameBoard.sizeOfX;
        int y = this.gameBoard.sizeOfY;

        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                Pawn tempPawn = this.gameBoard.getField(i, j).getPawn();
                if(tempPawn != null){
                    if(tempPawn.getColor().equals(ourColor)){
                        tempArray.add(tempPawn);
                    }
                }
            }
        }

        return tempArray;
    }

    public ArrayList<Field> calculateFieldsToAcheive() {
        ArrayList<Field> tempArray = new ArrayList<>();

        String desiredColor = this.botPawns.get(0).getDesiredColor();
        int x = this.gameBoard.sizeOfX;
        int y = this.gameBoard.sizeOfY;

        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                String fieldColor = gameBoard.getFieldType(i, j);
                if(fieldColor.equals(desiredColor)){
                    tempArray.add(gameBoard.getField(i, j));
                }
            }
        }

        return tempArray;
    }

    public String action(GameBoard gameBoard) {
        // TODO: make bot decide and return what to do
        if(!initialized){
            initializeBot();
            this.initialized = true;
        }

        Pawn pawn = getReadyPawn();

        getMoveDesicion(calculateAvailableMoves(pawn), fieldsToAcheive);

//        var moves = {
//                'type' : "move",
//                'x1' : -1,
//                'y1' : -1,
//                'x2' : -1,
//                'y2' : -1,
//                username: $("#username").val()
//};

        return "{ \"type\" : \"pass\" }";
    }
}
