package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements Actionable {
    Collection<Room> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public boolean isRoomHasDoor(String roomName, String doorId) {
        Room room = getRoom(roomName);
        if(room == null) return false;

        Door door = room.getDoor(doorId);
        return door != null;
    }

    private Room getRoom(String roomName) {
        for(Room room : rooms) {
            if(room.getName().equals(roomName))
                return room;
        }
        return null;
    }

    @Override
    public void execute(Action action) {
        for(Room room : rooms)
            room.execute(action);
    }
}
