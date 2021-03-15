package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.SensorEvent;

public interface EventProcessor {
    void processEvent(SensorEvent event);
}
