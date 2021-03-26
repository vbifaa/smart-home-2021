package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

import java.util.HashMap;

public class EventHandlerAdapter implements EventHandler  {
    private final EventProcessor processor;

    private final HashMap<String, SensorEventType> convertCCSensorEventType =
            new HashMap<String, SensorEventType>() {{
                put("LightIsOn", SensorEventType.LIGHT_ON);
                put("LightIsOff", SensorEventType.LIGHT_OFF);
                put("DoorIsOpen", SensorEventType.DOOR_OPEN);
                put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
                put("DoorIsLocked", SensorEventType.DOOR_CLOSED);
                put("DoorIsUnlocked", SensorEventType.DOOR_OPEN);
            }};

    public EventHandlerAdapter(EventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        processor.processEvent(convert(event));
    }

    private SensorEvent convert(CCSensorEvent event) {
        SensorEventType type = convertCCSensorEventType.get(event.getEventType());
        String objectId = event.getObjectId();
        return new SensorEvent(type, objectId);
    }
}
