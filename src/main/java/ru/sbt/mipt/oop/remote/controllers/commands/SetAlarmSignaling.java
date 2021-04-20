package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SignalingEvent;
import ru.sbt.mipt.oop.events.SignalingEventType;

public class SetAlarmSignaling extends Command {

    public SetAlarmSignaling(EventProcessor processor) {
        super(processor);
    }

    @Override
    Event getEvent() {
        return new SignalingEvent(SignalingEventType.ALARM, null);
    }
}
