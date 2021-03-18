package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;


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

        home.execute(checkDoorInHallAndTurnOff(event.getObjectId()));
    }

    private Action checkDoorInHallAndTurnOff(String doorId) {
        return (obj)->{
            if(obj instanceof Room && ((Room) obj).getName().equals("hall"))
                ((Room) obj).execute(findDoorAndTurnOff(doorId));
        };
    }

    private Action findDoorAndTurnOff(String doorId) {
        return (obj)->{
            if(obj instanceof Door && ((Door) obj).getId().equals(doorId))
                home.execute(turnOff());
        };
    }

    private Action turnOff() {
        return (obj)->{
            if(obj instanceof Light)
                ((Light) obj).setOn(false);
        };
    }
}
