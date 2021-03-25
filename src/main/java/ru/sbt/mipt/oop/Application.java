package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.decorators.SignalingDecorator;
import ru.sbt.mipt.oop.event.processors.*;
import ru.sbt.mipt.oop.file.readers.FileContentReaderImpl;
import ru.sbt.mipt.oop.file.readers.SmartHomeJsonReader;
import ru.sbt.mipt.oop.file.readers.SmartHomeReader;
import ru.sbt.mipt.oop.signaling.Signaling;

import java.util.Arrays;


public class Application {

    public static void main(String... args) {
        SmartHomeReader smartHomeReader = new SmartHomeJsonReader(new FileContentReaderImpl());
        SmartHome smartHome = smartHomeReader.read("smart-home-1.js");


        Signaling signaling = new Signaling();
        EventProcessor handler = new CompositeHandler(
                Arrays.asList(
                        new LightEventProcessor(smartHome),
                        new DoorEventProcessor(smartHome),
                        new HallDoorEventProcessor(smartHome),
                        new SignalingEventProcessor(signaling)
                )
        );
        EventCreatorConsumer consumer = new EventCreatorConsumer(
                new SignalingDecorator(
                        handler, signaling, new SmsSenderImpl()
                ),
                new EventCreatorImpl()
        );
        consumer.processEvents();
    }

}
