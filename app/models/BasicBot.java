package models;

import models.Field.Field;
import models.Pawn.Pawn;

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

    private ArrayList<Field> calculateAvailableMoves() {
        // TODO: wszystkie mozliwe ruchy dookoła
        return null;
    }

    private double calculateDistance(Field candidate, Field destination) {
        // TODO: dystans
        return 0;
    }

    private Field getDestinationField(ArrayList<Field> winingFields){
        //TODO: oblicz dystans dla kazdego i zwroc najlepszego

        return null;
    }

    public String action(GameBoard gameBoard) {
        // TODO: make bot decide and return what to do
        if(!initialized){
            initializeBot();
            this.initialized = true;
        }


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
}
