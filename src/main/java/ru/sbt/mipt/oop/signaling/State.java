package ru.sbt.mipt.oop.signaling;

interface State {
    void activate(String code);
    void deactivate(String code);
    void alarm();
}
