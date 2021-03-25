package ru.sbt.mipt.oop.signaling;

public class DeactivateState implements State {
    protected final Signaling signaling;

    protected DeactivateState(Signaling signaling) {
        this.signaling = signaling;
    }

    @Override
    public void activate(String code) {
        signaling.setCode(code);
        signaling.setState(new ActivateState(signaling));
    }

    @Override
    public void deactivate(String code) {}

    @Override
    public void alarm() {
        signaling.setState(new AlarmState(signaling));
    }
}
