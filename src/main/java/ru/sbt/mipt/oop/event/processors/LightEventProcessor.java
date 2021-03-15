package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;


public class LightEventProcessor implements EventProcessor {
    private final SmartHome home;

    public LightEventProcessor(SmartHome home) {
        this.home = home;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if(!isValidEvent(event.getType())) return;

        boolean isOn = event.getType() == SensorEventType.LIGHT_ON;
        String id = event.getObjectId();
        Action turnOnOff = (obj)->{
            if(obj instanceof Light && ((Light) obj).getId().equals(id))
                ((Light) obj).setOn(isOn);
        };
        home.execute(turnOnOff);
    }

    private boolean isValidEvent(SensorEventType type) {
        return type == SensorEventType.LIGHT_OFF || type == SensorEventType.LIGHT_ON;
    }

}
