package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.commands.CommandSenderImpl;

import java.util.HashMap;


public class DoorEventProcessor implements EventProcessor {
    private final SmartHome home;
    private final HashMap<SensorEventType,Boolean> closeOpen =
            new HashMap<SensorEventType, Boolean>(){{
                put(SensorEventType.DOOR_CLOSED, false);
                put(SensorEventType.DOOR_OPEN, true);
            }};

    public DoorEventProcessor(SmartHome home) {
        this.home = home;
    }

    @Override
    public void processEvent(SensorEvent event) {
        for (Room room : home.getRooms()) {
            Door door = room.getDoor(event.getObjectId());
            boolean isOpen = closeOpen.get(event.getType());
            if(door != null)
                closeOrOpenDoor(door, room, isOpen);
        }
    }

    private void closeOrOpenDoor(Door door, Room room, boolean isOpen) {
        door.setOpen(isOpen);
        printMessageCloseOrOpenDoor(door, room, isOpen);
        if(!isOpen && room.getName().equals("hall")) {
            new LightEventProcessor(home, new CommandSenderImpl()).turnOffAllLights();
        }
    }

    private void printMessageCloseOrOpenDoor(Door door, Room room, boolean isOpen) {
        String message = "Door " + door.getId() + " in room " + room.getName() + " was ";
        message += isOpen ? "opened." : "off.";
        System.out.println(message);
    }
}
