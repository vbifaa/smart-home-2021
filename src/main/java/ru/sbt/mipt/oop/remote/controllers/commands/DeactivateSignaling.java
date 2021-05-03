package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SignalingEvent;
import ru.sbt.mipt.oop.events.SignalingEventType;

public class DeactivateSignaling extends Command {
    private final String code;

    public DeactivateSignaling(EventProcessor processor, String code) {
        super(processor);
        this.code = code;
    }

    @Override
    Event getEvent() {
        return new SignalingEvent(SignalingEventType.ALARM_DEACTIVATE, code);
    }
}
