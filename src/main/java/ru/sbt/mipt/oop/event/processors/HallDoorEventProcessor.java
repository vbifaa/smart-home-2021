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
        if(!home.isRoomHasDoor("hall", event.getObjectId())) return;

        Action turnOff = (obj)->{
            if(obj instanceof Light)
                ((Light) obj).setOn(false);
        };
        home.execute(turnOff);
    }
}
