package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.event.processors.EventProcessorImpl;
import ru.sbt.mipt.oop.file.readers.FileContentReaderImpl;
import ru.sbt.mipt.oop.file.readers.SmartHomeJsonReader;
import ru.sbt.mipt.oop.file.readers.SmartHomeReader;


public class Application {

    public static void main(String... args) {
        SmartHomeReader smartHomeReader = new SmartHomeJsonReader(new FileContentReaderImpl());
        SmartHome smartHome = smartHomeReader.read("smart-home-1.js");

        EventCreatorConsumer consumer = new EventCreatorConsumer(
                new EventProcessorImpl(smartHome), new EventCreatorImpl()
        );
        consumer.processEvents();
    }

}
