package ru.sbt.mipt.oop.signaling;

public class ActivateState extends State {

    protected ActivateState(Signaling signaling) {
        super(signaling);
    }

    @Override
    protected void activate(String code) {}

    @Override
    protected void deactivate(String code) {
        if(signaling.getCode().equals(code))
            signaling.setState(new DeactivateState(signaling));
        else
            signaling.setState(new AlarmState(signaling));
    }
}
