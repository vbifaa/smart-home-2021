package ru.sbt.mipt.oop.home.build;

public class RoomCharacteristic {
    public final int lightsCount;
    public final int doorsCount;
    public final String name;

    public RoomCharacteristic(int lightsCount, int doorsCount, String name) {
        this.lightsCount = lightsCount;
        this.doorsCount = doorsCount;
        this.name = name;
    }
}
