package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

public class CloseDoor extends Command {
    private final String doorId;

    public CloseDoor(EventProcessor processor, String doorId) {
        super(processor);
        this.doorId = doorId;
    }

    @Override
    Event getEvent() {
        return new SensorEvent(SensorEventType.DOOR_CLOSED, doorId);
    }
}
