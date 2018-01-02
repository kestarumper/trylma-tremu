package models;

import akka.actor.ActorRef;

public class BasicBot extends User {

    public BasicBot(String name, String csrf) {
        super(name.concat("Bot"), csrf);
    }

    public String action(GameBoard gameBoard) {
        // TODO: make bot decide and return what to do

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
