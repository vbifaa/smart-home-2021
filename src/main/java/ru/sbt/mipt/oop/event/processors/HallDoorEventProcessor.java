package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandSenderImpl;

import static ru.sbt.mipt.oop.event.processors.LightUtils.turnOffAllLightsInRoom;

public class HallDoorEventProcessor implements EventProcessor {
    private final SmartHome home;

    public HallDoorEventProcessor(SmartHome home) {
        this.home = home;
    }

    private boolean isValidEvent(SensorEventType type) {
        return type == SensorEventType.DOOR_CLOSED;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if(!isValidEvent(event.getType())) return;

        for (Room room : home.getRooms()) {
            Door door = room.getDoor(event.getObjectId());
            if(door != null && room.getName().equals("hall"))
                turnOffAllLights();
        }
    }

    private void turnOffAllLights() {
        CommandSender sender = new CommandSenderImpl();
        for(Room roomHome : home.getRooms()) {
            turnOffAllLightsInRoom(roomHome, sender);
        }
    }
}
