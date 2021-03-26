package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.List;

public class CompositeHandler implements EventProcessor {
    private final List<EventProcessor> eventProcessors;


    public CompositeHandler(List<EventProcessor> processors) {
        this.eventProcessors = processors;
    }

    @Override
    public void processEvent(Event event) {
        System.out.println("Got event: " + event);
        for(EventProcessor processor : eventProcessors) {
            processor.processEvent(event);
        }
    }
}
