package ru.sbt.mipt.oop.actors;

import java.util.List;

public class ActorsConsumer {
    private final List<Actor> actors;

    public ActorsConsumer(List<Actor> actors) {
        this.actors = actors;
    }

    public void start() {
        for(Actor actor : actors)
            new Thread(actor).start();
    }
}
