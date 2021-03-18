package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

public interface EventProcessor {
    void processEvent(Event event);
}
