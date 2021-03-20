package ru.sbt.mipt.oop.signaling;

public class AlarmState extends State {

    protected AlarmState(Signaling signaling) {
        super(signaling);
    }

    @Override
    protected void activate(String code) {}

    @Override
    protected void deactivate(String code) {}
}
