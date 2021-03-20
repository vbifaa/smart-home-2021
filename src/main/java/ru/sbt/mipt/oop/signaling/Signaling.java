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

    public State getState() {
        return state;
    }

    public void setAlarm() {
        state = new AlarmState(this);
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
}
