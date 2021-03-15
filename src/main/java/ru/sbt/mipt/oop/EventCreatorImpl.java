package ru.sbt.mipt.oop;

public class EventCreatorImpl implements  EventCreator {

    @Override
    public SensorEvent getNextEvent() {
        if (Math.random() < 0.05) return null;
        SensorEventType sensorEventType = SensorEventType.values()[(int) (4 * Math.random())];
        String objectId = "" + ((int) (10 * Math.random()));
        return new SensorEvent(sensorEventType, objectId);
    }
}
