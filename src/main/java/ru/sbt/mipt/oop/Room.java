package ru.sbt.mipt.oop;

import java.util.Collection;

public class Room {
    private final Collection<Light> lights;
    private final Collection<Door> doors;
    private final String name;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public Collection<Light> getLights() {
        return lights;
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public String getName() {
        return name;
    }

    public Door getDoor(String doorId) {
        for(Door door : doors)
            if(door.getId().equals(doorId))
                return door;
        return null;
    }

    public Light getLight(String lightId) {
        for (Light light : lights)
            if (light.getId().equals(lightId))
                return light;
        return null;
    }
}
