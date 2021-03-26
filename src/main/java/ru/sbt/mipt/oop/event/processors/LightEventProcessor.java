package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;


public class LightEventProcessor implements EventProcessor {
    private final SmartHome home;

    public LightEventProcessor(SmartHome home) {
        this.home = home;
    }

    private boolean isValidEvent(EventType type) {
        return type == SensorEventType.LIGHT_OFF || type == SensorEventType.LIGHT_ON;
    }

    @Override
    public void processEvent(Event event) {
        if(!isValidEvent(event.getType())) return;

        boolean isOn = event.getType() == SensorEventType.LIGHT_ON;
        String id = ((SensorEvent) event).getObjectId();
        home.execute(turnOnOff(id, isOn));
    }

    private Action turnOnOff(String lightId, boolean isOn) {
        return (obj)->{
            if(obj instanceof Light && ((Light) obj).getId().equals(lightId))
                ((Light) obj).setOn(isOn);
        };
    }

}
