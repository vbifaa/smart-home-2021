package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;

public class LightUtils {
    public static void turnOffAllLights(Room room, CommandSender sender) {
        for(Light light : room.getLights()) {
            light.setOn(false);
            SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
            sender.sendCommand(command);
        }
    }
}
