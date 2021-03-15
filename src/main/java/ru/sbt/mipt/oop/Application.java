package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.event.processors.DoorEventProcessor;
import ru.sbt.mipt.oop.event.processors.EventProcessorImpl;
import ru.sbt.mipt.oop.event.processors.HallDoorEventProcessor;
import ru.sbt.mipt.oop.event.processors.LightEventProcessor;
import ru.sbt.mipt.oop.file.readers.FileContentReaderImpl;
import ru.sbt.mipt.oop.file.readers.SmartHomeJsonReader;
import ru.sbt.mipt.oop.file.readers.SmartHomeReader;

import java.util.Arrays;


public class Application {

    public static void main(String... args) {
        SmartHomeReader smartHomeReader = new SmartHomeJsonReader(new FileContentReaderImpl());
        SmartHome smartHome = smartHomeReader.read("smart-home-1.js");


        EventCreatorConsumer consumer = new EventCreatorConsumer(
                new EventProcessorImpl(
                        Arrays.asList(
                                new LightEventProcessor(smartHome),
                                new DoorEventProcessor(smartHome),
                                new HallDoorEventProcessor(smartHome)
                        )
                ),
                new EventCreatorImpl()
        );
        consumer.processEvents();
    }

}
