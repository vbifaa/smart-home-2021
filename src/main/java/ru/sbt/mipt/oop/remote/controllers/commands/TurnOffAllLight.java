package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

public class TurnOffAllLight extends Command {

    public TurnOffAllLight(EventProcessor processor) {
        super(processor);
    }

    @Override
    Event getEvent() {
        return new SensorEvent(SensorEventType.LIGHT_OFF, null);
    }

}
