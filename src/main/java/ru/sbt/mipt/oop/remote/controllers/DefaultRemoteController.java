package ru.sbt.mipt.oop.remote.controllers;

import rc.RemoteControl;
import ru.sbt.mipt.oop.remote.controllers.commands.Command;

import java.util.Map;

public class DefaultRemoteController implements RemoteControl {

    private final Map<String, Command> commands;

    public DefaultRemoteController(Map<String, Command> commands) {
        this.commands = commands;
    }


    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        Command command = commands.get(buttonCode);
        if(command != null) command.execute();
    }
}
