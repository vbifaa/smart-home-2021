package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.event.processors.EventProcessorImpl;
import ru.sbt.mipt.oop.file.readers.FileContentReaderImpl;
import ru.sbt.mipt.oop.file.readers.SmartHomeJsonReader;
import ru.sbt.mipt.oop.file.readers.SmartHomeReader;


public class Application {

    public static void main(String... args) {
        SmartHomeReader smartHomeReader = new SmartHomeJsonReader(new FileContentReaderImpl());
        SmartHome smartHome = smartHomeReader.buildSmartHome("smart-home-1.js");

        EventProcessor processor = new EventProcessorImpl(smartHome);
        EventCreator creator = new EventCreatorImpl();

        SensorEvent event = creator.getNextEvent();
        while (event != null) {
            processor.processEvent(event);
            event = creator.getNextEvent();
        }
    }

}
