package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.SensorEventType;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.commands.CommandSenderImpl;

import java.util.HashMap;

public class EventProcessorImpl implements EventProcessor {

    private enum EventProcessorType {
        LIGHT, DOOR
    }

    private final HashMap<EventProcessorType, EventProcessor> eventProcessors;

    private final HashMap<SensorEventType, EventProcessorType> eventProcessorTypes =
            new HashMap<SensorEventType, EventProcessorType>(){{
                put(SensorEventType.LIGHT_OFF, EventProcessorType.LIGHT);
                put(SensorEventType.LIGHT_ON, EventProcessorType.LIGHT);
                put(SensorEventType.DOOR_OPEN, EventProcessorType.DOOR);
                put(SensorEventType.DOOR_CLOSED, EventProcessorType.DOOR);
            }};

    public EventProcessorImpl(SmartHome home) {
        this.eventProcessors =
                new HashMap<EventProcessorType, EventProcessor>() {{
                    put(EventProcessorType.LIGHT, new LightEventProcessor(home, new CommandSenderImpl()));
                    put(EventProcessorType.DOOR, new DoorEventProcessor(home));
                }};
    }

    @Override
    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        EventProcessorType ProcessorType = eventProcessorTypes.get(event.getType());
        EventProcessor processor = eventProcessors.get(ProcessorType);
        processor.processEvent(event);
    }
}
