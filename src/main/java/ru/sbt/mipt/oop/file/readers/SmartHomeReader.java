package ru.sbt.mipt.oop.file.readers;

import ru.sbt.mipt.oop.SmartHome;

public interface SmartHomeReader {
    SmartHome read(String fileName);
}
