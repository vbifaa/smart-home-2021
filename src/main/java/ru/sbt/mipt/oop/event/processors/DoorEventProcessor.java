package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;


public class DoorEventProcessor implements EventProcessor {
    private final SmartHome home;

    public DoorEventProcessor(SmartHome home) {
        this.home = home;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if(!isValidEvent(event.getType())) return;

        boolean isOpen = event.getType() == SensorEventType.DOOR_OPEN;
        String id = event.getObjectId();
        Action openClose = (obj)->{
            if(obj instanceof Door && ((Door) obj).getId().equals(id))
                ((Door) obj).setOpen(isOpen);
        };
        home.execute(openClose);
    }

    private boolean isValidEvent(SensorEventType type) {
        return type == SensorEventType.DOOR_CLOSED || type == SensorEventType.DOOR_OPEN;
    }
}
