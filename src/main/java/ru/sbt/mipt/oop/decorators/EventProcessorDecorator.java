package ru.sbt.mipt.oop.decorators;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;

public class EventProcessorDecorator implements EventProcessor {
    EventProcessor processor;

    public EventProcessorDecorator(EventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void processEvent(Event event) {
        processor.processEvent(event);
    }
}
