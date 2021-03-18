package ru.sbt.mipt.oop.signaling;

public class Signaling {
    private String code = null;
    private State state;


    public Signaling() {
        this.state = new DeactivateState(this);
    }

    public void activate(String code) {
        state.activate(code);
    }

    public void deactivate(String code) {
        state.deactivate(code);
    }

    public boolean isAlarm() {
        return state instanceof AlarmState;
    }

    protected String getCode() {
        return code;
    }

    protected void setCode(String code) {
        this.code = code;
    }

    protected void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
