package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

public class RoomLightEventProcessor implements EventProcessor {
    private final SmartHome home;

    public RoomLightEventProcessor(SmartHome home) {
        this.home = home;
    }

    private boolean isValidEvent(EventType type) {
        return type == SensorEventType.ROOM_LIGHT_ON;
    }

    @Override
    public void processEvent(Event event) {
        if(!isValidEvent(event.getType())) return;

        boolean isOn = event.getType() == SensorEventType.ROOM_LIGHT_ON;
        home.execute(turnOnOffLightInRoom(((SensorEvent) event).getObjectId(), isOn));
    }

    private Action turnOnOffLightInRoom(String room, boolean isOn) {
        return (obj)->{
            if(obj instanceof Room && ((Room) obj).getName().equals(room))
                ((Room) obj).execute(turnOnOff(isOn));
        };
    }

    private Action turnOnOff(boolean isOn) {
        return (obj)->{
            if(obj instanceof Light)
                ((Light) obj).setOn(isOn);
        };
    }
}
