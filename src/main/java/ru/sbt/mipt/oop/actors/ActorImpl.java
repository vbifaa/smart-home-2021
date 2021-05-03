package ru.sbt.mipt.oop.actors;

import ru.sbt.mipt.oop.remote.controllers.ActorRemoteController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActorImpl implements Actor {

    private final ActorRemoteController controller;
    private final List<String> buttons = new ArrayList<>(
            Arrays.asList("a", "b", "c", "d", "1", "2", "3", "4")
    );

    public ActorImpl(ActorRemoteController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (Math.random() > 0.5) {
            controller.onButtonPressed(generateButton(), "");
            controller.runCommands();
        }
    }



    @Override
    public String generateButton() {
        int id = (int)(Math.random() * 8);
        return buttons.get(id);
    }
}
