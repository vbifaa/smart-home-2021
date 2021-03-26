package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.SensorEvent;

public interface EventCreator {
    SensorEvent getNextEvent();
}
