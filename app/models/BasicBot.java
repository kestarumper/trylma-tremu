package models;

import akka.actor.ActorRef;

public class BasicBot extends User {

    public BasicBot(String name, String csrf) {
        super(name.concat("Bot"), csrf);
    }

    public String action() {
        // TODO: make bot decide and return what to do
        return "{ \"type\" : \"move\" }";
    }
}
