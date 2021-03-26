package ru.sbt.mipt.oop.signaling;

public class ActivateState implements State {
    protected final Signaling signaling;

    protected ActivateState(Signaling signaling) {
        this.signaling = signaling;
    }

    @Override
    public void activate(String code) {}

    @Override
    public void deactivate(String code) {
        if(signaling.getCode().equals(code))
            signaling.setState(new DeactivateState(signaling));
        else
            signaling.setState(new AlarmState(signaling));
    }

    @Override
    public void alarm() {
        signaling.setState(new AlarmState(signaling));
    }
}
