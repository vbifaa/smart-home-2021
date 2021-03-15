package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;


public class LightEventProcessor implements EventProcessor {
    private final SmartHome home;

    public LightEventProcessor(SmartHome home) {
        this.home = home;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if(!isValidEvent(event.getType())) return;

        for (Room room : home.getRooms()) {
            Light light = room.getLight(event.getObjectId());
            boolean isOn = event.getType() == SensorEventType.LIGHT_ON;
            if(light != null)
                turnLightOnOff(light, room, isOn);
        }
    }

    private boolean isValidEvent(SensorEventType type) {
        return type == SensorEventType.LIGHT_OFF || type == SensorEventType.LIGHT_ON;
    }

    private void turnLightOnOff(Light light, Room room, boolean isOn) {
        light.setOn(isOn);
        printMessageLightTurnedOnOff(light, room, isOn);
    }

    private void printMessageLightTurnedOnOff(Light light, Room room, boolean isOn) {
        String message = "Light " + light.getId() + " in room " + room.getName() + " was turned ";
        message += isOn ? "on." : "off.";
        System.out.println(message);
    }

}
