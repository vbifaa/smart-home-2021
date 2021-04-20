package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.*;

public class ActivateSignaling extends Command {
    private final String code;

    public ActivateSignaling(EventProcessor processor, String code) {
        super(processor);
        this.code = code;
    }

    @Override
    Event getEvent() {
        return new SignalingEvent(SignalingEventType.ALARM_ACTIVATE, code);
    }
}
