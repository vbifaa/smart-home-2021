package ru.sbt.mipt.oop.decorators;

import ru.sbt.mipt.oop.SmsSender;
import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SignalingEvent;
import ru.sbt.mipt.oop.signaling.Signaling;

public class SignalingDecorator implements EventProcessor {
    private final EventProcessor processor;
    private final Signaling signaling;
    private final SmsSender sender;
    private final String message = "Attention! There is someone in the house!\n";

    public SignalingDecorator(
            EventProcessor processor,
            Signaling signaling,
            SmsSender sender
    ) {
        this.processor = processor;
        this.signaling = signaling;
        this.sender = sender;
    }

    @Override
    public void processEvent(Event event) {
        if(signaling.isDeactivate()) {
            processor.processEvent(event);
        } else if(signaling.isAlarm()) {
            sender.send(message);
        } else {
            tryDeactivate(event);
        }
    }

    private void tryDeactivate(Event event) {
        if(event instanceof SignalingEvent) {
            processor.processEvent(event);
        } else {
            signaling.alarm();
            sender.send(message);
        }
    }
}
