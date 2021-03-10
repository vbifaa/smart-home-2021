package ru.sbt.mipt.oop.file.readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileContentReaderImpl implements  FileContentReader {

    @Override
    public String getFileContent(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
