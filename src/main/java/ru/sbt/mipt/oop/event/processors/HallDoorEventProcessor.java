package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class HallDoorEventProcessor implements EventProcessor {
    private final SmartHome home;

    public HallDoorEventProcessor(SmartHome home) {
        this.home = home;
    }

    private boolean isValidEvent(SensorEventType type) {
        return type == SensorEventType.DOOR_CLOSED;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if(!isValidEvent(event.getType())) return;

        String id = event.getObjectId();

        Action turnOff = (obj)->{
            if(obj instanceof Light)
                ((Light) obj).setOn(false);
        };

        Action findDoorAndTurnOff = (obj)->{
            if(obj instanceof Room && ((Room) obj).getName().equals("hall")) {
                Door door = ((Room) obj).getDoor(id);
                if(door != null)
                    home.execute(turnOff);
            }

        };
        home.execute(findDoorAndTurnOff);
    }
}
