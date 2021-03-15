package ru.sbt.mipt.oop.home.build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

public class HomeBuilder {

    public static void main(String[] args) throws IOException {
        Collection<Room> rooms = new RoomBuilder().createRooms(buildRoomsCharacteristics());
        SmartHome smartHome = new SmartHome(rooms);

        buildJavaScriptFile(buildJsonStringSmartHome(smartHome));
    }

    private static Collection<RoomCharacteristic> buildRoomsCharacteristics() {
        return Arrays.asList(
                new RoomCharacteristic(2, 1, "kitchen"),
                new RoomCharacteristic(1, 1, "bathroom"),
                new RoomCharacteristic(3, 1, "bedroom"),
                new RoomCharacteristic(3, 1, "hall")
        );
    }

    private static String buildJsonStringSmartHome(SmartHome smartHome) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(smartHome);
        System.out.println(jsonString);
        return jsonString;
    }

    private static void buildJavaScriptFile(String content) throws IOException {
        Path path = Paths.get("output.js");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        }
    }

}
