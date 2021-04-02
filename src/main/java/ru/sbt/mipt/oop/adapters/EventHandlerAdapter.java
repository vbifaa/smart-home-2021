package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

import java.util.Map;

public class EventHandlerAdapter implements EventHandler  {
    private final EventProcessor processor;
    private final Map<String, SensorEventType> convertCCSensorEventType;

    public EventHandlerAdapter(EventProcessor processor, Map<String, SensorEventType> convertCCSensorEventType) {
        this.processor = processor;
        this.convertCCSensorEventType = convertCCSensorEventType;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        if(event == null) return;
        processor.processEvent(convert(event));
    }

    private SensorEvent convert(CCSensorEvent event) {
        SensorEventType type = convertCCSensorEventType.get(event.getEventType());
        String objectId = event.getObjectId();
        return new SensorEvent(type, objectId);
    }
}
