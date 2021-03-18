package ru.sbt.mipt.oop.decorators;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.signaling.AlarmState;
import ru.sbt.mipt.oop.signaling.DeactivateState;
import ru.sbt.mipt.oop.signaling.Signaling;

public class SignalingDecorator extends EventProcessorDecorator {
    private final Signaling signaling;

    public SignalingDecorator(EventProcessor processor, Signaling signaling) {
        super(processor);
        this.signaling = signaling;
    }

    @Override
    public void processEvent(Event event) {
        if(signaling.getState() instanceof DeactivateState){
            processor.processEvent(event);
        } else if(signaling.getState() instanceof AlarmState) {
            System.out.print("Attention! There is someone in the house.");
        } else {
            tryDeactivate(event);
        }
    }
    
    private void tryDeactivate(Event event) {
        if(event instanceof SensorEvent){
            signaling.setState(new AlarmState(signaling));
        } else {
            processor.processEvent(event);
        }
    }
}
