package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;

import java.util.HashMap;


public class LightEventProcessor implements EventProcessor {
    private final SmartHome home;
    private final CommandSender sender;
    private final HashMap<SensorEventType,Boolean> turnOnOff =
            new HashMap<SensorEventType, Boolean>(){{
                put(SensorEventType.LIGHT_OFF, false);
                put(SensorEventType.LIGHT_ON, true);
            }};

    public LightEventProcessor(SmartHome home, CommandSender sender) {
        this.home = home;
        this.sender = sender;
    }


    @Override
    public void processEvent(SensorEvent event) {
        for (Room room : home.getRooms()) {
            Light light = room.getLight(event.getObjectId());
            boolean isOn = turnOnOff.get(event.getType());
            if(light != null)
                turnLightOnOff(light, room, isOn);
        }
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

    public void turnOffAllLights() {
        for(Room room : home.getRooms()) {
            turnOffAllLightsInRoom(room);
        }
    }

    public void turnOffAllLightsInRoom(Room room) {
        for(Light light : room.getLights()) {
            light.setOn(false);
            SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
            sender.sendCommand(command);
        }
    }
}
