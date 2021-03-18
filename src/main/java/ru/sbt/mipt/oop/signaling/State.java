package ru.sbt.mipt.oop.signaling;

abstract class State {
    protected final Signaling signaling;

    protected State(Signaling signaling) {
        this.signaling = signaling;
    }

    abstract void activate(String code);
    abstract void deactivate(String code);
}
