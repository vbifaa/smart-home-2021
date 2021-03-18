package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;


public class DoorEventProcessor implements EventProcessor {
    private final SmartHome home;

    public DoorEventProcessor(SmartHome home) {
        this.home = home;
    }

    private boolean isValidEvent(SensorEventType type) {
        return type == SensorEventType.DOOR_CLOSED || type == SensorEventType.DOOR_OPEN;
    }

    @Override
    public void processEvent(Event event) {
        if(!isValidEvent((SensorEventType) event.getType())) return;

        boolean isOpen = event.getType() == SensorEventType.DOOR_OPEN;
        String id = ((SensorEvent) event).getObjectId();
        home.execute(openClose(id, isOpen));
    }

    private Action openClose(String doorId, boolean isOpen) {
        return (obj)->{
            if(obj instanceof Door && ((Door) obj).getId().equals(doorId))
                ((Door) obj).setOpen(isOpen);
        };
    }
}
