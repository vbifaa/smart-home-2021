package ru.sbt.mipt.oop.remote.controllers;

import rc.RemoteControl;
import ru.sbt.mipt.oop.remote.controllers.commands.Command;

import java.util.*;

public class ActorRemoteController implements RemoteControl {

    private final Map<String, Command> buttonCommands;
    private final List<ActorRemoteController> actors;
    private final Queue<Command> commands = new LinkedList<>();

    public ActorRemoteController(Map<String, Command> buttonCommands, List<ActorRemoteController> actors) {
        this.buttonCommands = buttonCommands;
        this.actors = actors;
    }

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        Command command = buttonCommands.get(buttonCode);
        if(command == null) return;

        for(ActorRemoteController actor : actors) {
            actor.addCommand(command);
        }
        commands.add(command);
    }

    private synchronized void addCommand(Command command) {
        commands.add(command);
    }

    public void runCommands() {
        while(commands.size() > 0) {
            Command command = commands.remove();
            if(command != null) command.execute();
        }
    }
}
