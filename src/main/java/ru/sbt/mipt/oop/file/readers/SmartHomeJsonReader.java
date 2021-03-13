package ru.sbt.mipt.oop.file.readers;

import com.google.gson.Gson;
import ru.sbt.mipt.oop.SmartHome;

public class SmartHomeJsonReader implements SmartHomeReader {
    private final Gson gson = new Gson();
    private final FileContentReader fileReader;

    public SmartHomeJsonReader(FileContentReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public SmartHome read(String fileName) {
        String fileContent = fileReader.getFileContent(fileName);
        return gson.fromJson(fileContent, SmartHome.class);
    }
}
