package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;


public class LightEventProcessor implements EventProcessor {
    private final SmartHome home;

    public LightEventProcessor(SmartHome home) {
        this.home = home;
    }

    private boolean isValidEvent(SensorEventType type) {
        return type == SensorEventType.LIGHT_OFF || type == SensorEventType.LIGHT_ON;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if(!isValidEvent(event.getType())) return;

        boolean isOn = event.getType() == SensorEventType.LIGHT_ON;
        String id = event.getObjectId();
        home.execute(turnOnOff(id, isOn));
    }

    private Action turnOnOff(String lightId, boolean isOn) {
        return (obj)->{
            if(obj instanceof Light && ((Light) obj).getId().equals(lightId))
                ((Light) obj).setOn(isOn);
        };
    }

}
