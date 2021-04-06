package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;

public abstract class Command {
    private final EventProcessor processor;

    public Command(EventProcessor processor) {
        this.processor = processor;
    }

    public void execute() {
        processor.processEvent(getEvent());
    }

    abstract Event getEvent();
}
