package ru.sbt.mipt.oop.events;

public class SignalingEvent implements Event {
    private final SignalingEventType type;
    private final String code;

    public SignalingEvent(SignalingEventType type, String code) {
        this.type = type;
        this.code = code;
    }


    public SignalingEventType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
