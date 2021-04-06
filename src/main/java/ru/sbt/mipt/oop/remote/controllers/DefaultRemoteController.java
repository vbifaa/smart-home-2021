package ru.sbt.mipt.oop.remote.controllers;

import rc.RemoteControl;
import ru.sbt.mipt.oop.remote.controllers.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class DefaultRemoteController implements RemoteControl {

    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        Command command = commands.get(buttonCode);
        command.execute();
    }

    public void registerButton(String buttonCode, Command command) {
        commands.put(buttonCode, command);
    }
}
