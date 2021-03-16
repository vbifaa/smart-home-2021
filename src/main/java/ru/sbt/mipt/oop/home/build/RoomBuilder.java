package ru.sbt.mipt.oop.home.build;

import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoomBuilder {
    private final List<Room> rooms = new ArrayList<>();
    private int lightIdCounter = 0;
    private int doorIdCounter = 0;

    public Collection<Room> createRooms(Collection<RoomCharacteristic> characteristics) {
        for(RoomCharacteristic characteristic : characteristics) {
            Collection<Light> lights = createLights(characteristic.lightsCount);
            Collection<Door> doors = createDoors(characteristic.doorsCount);
            String name = characteristic.name;

            rooms.add(new Room(lights, doors, name));
        }
        return rooms;
    }

    private Collection<Light> createLights(int lightsCount) {
        Collection<Light> lights = new ArrayList<>();
        for(int i = 0; i < lightsCount; i++)
            lights.add(new Light(String.valueOf(lightIdCounter++), false));
        return lights;
    }

    private Collection<Door> createDoors(int doorsCount) {
        Collection<Door> doors = new ArrayList<>();
        for(int i = 0; i < doorsCount; i++)
            doors.add(new Door(false, String.valueOf(doorIdCounter++)));
        return doors;
    }
}
