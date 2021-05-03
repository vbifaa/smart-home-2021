package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

public class OpenDoor extends Command {
    private final String doorId;

    public OpenDoor(EventProcessor processor, String doorId) {
        super(processor);
        this.doorId = doorId;
    }

    @Override
    Event getEvent() {
        return new SensorEvent(SensorEventType.DOOR_OPEN, doorId);
    }
}
