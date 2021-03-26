package ru.sbt.mipt.oop.signaling;

public class AlarmState implements State {
    protected final Signaling signaling;

    protected AlarmState(Signaling signaling) {
        this.signaling = signaling;
    }

    @Override
    public void activate(String code) {}

    @Override
    public void deactivate(String code) {}

    @Override
    public void alarm() {}
}
