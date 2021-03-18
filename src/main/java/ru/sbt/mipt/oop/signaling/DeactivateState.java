package ru.sbt.mipt.oop.signaling;

public class DeactivateState extends State {

    protected DeactivateState(Signaling signaling) {
        super(signaling);
    }

    @Override
    protected void activate(String code) {
        signaling.setCode(code);
        signaling.setState(new ActivateState(signaling));
    }

    @Override
    protected void deactivate(String code) {}
}
