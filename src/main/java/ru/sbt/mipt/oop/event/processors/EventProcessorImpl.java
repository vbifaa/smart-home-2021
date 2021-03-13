package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.commands.CommandSenderImpl;

import java.util.ArrayList;
import java.util.List;

public class EventProcessorImpl implements EventProcessor {
    private final List<EventProcessor> eventProcessors;


    public EventProcessorImpl(SmartHome home) {
        this.eventProcessors =
                new ArrayList<EventProcessor>() {{
                    add(new LightEventProcessor(home, new CommandSenderImpl()));
                    add(new DoorEventProcessor(home));
                }};
    }

    @Override
    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        for(EventProcessor processor : eventProcessors) {
            processor.processEvent(event);
        }
    }
}
