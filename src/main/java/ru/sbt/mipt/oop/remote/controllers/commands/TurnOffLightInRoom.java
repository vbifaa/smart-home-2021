package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

public class TurnOffLightInRoom extends Command {
    private final String roomId;

    public TurnOffLightInRoom(EventProcessor processor, String roomId) {
        super(processor);
        this.roomId = roomId;
    }

    @Override
    Event getEvent() {
        return new SensorEvent(SensorEventType.ROOM_LIGHT_OFF, roomId);
    }
}
