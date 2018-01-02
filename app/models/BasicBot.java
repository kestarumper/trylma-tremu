package models;

import akka.actor.ActorRef;

public class BasicBot extends User {

    public BasicBot(String name, String csrf) {
        super(name.concat("Bot"), csrf);
    }
}
