package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.event.processors.EventProcessor;

public class EventCreatorConsumer {
    private final EventProcessor processor;
    private final EventCreator creator;

    public EventCreatorConsumer(EventProcessor processor, EventCreator creator) {
        this.processor = processor;
        this.creator = creator;
    }

    public void processEvents() {
        SensorEvent event = creator.getNextEvent();
        while (event != null) {
            processor.processEvent(event);
            event = creator.getNextEvent();
        }
    }
}
