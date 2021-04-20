package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SignalingEvent;
import ru.sbt.mipt.oop.events.SignalingEventType;
import ru.sbt.mipt.oop.signaling.Signaling;

public class SignalingEventProcessor implements EventProcessor {
    private final Signaling signaling;

    public SignalingEventProcessor(Signaling signaling) {
        this.signaling = signaling;
    }

    private boolean isValidEvent(EventType type){
        return type instanceof SignalingEventType;
    }

    @Override
    public void processEvent(Event event) {
        if(!isValidEvent(event.getType())) return;

        String code = ((SignalingEvent) event).getCode();
        if(event.getType() == SignalingEventType.ALARM_ACTIVATE)
            signaling.activate(code);
        else if(event.getType() == SignalingEventType.ALARM_DEACTIVATE)
            signaling.deactivate(code);
        else
            signaling.alarm();
    }
}
