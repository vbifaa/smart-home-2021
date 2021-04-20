package ru.sbt.mipt.oop.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.event.processors.*;
import ru.sbt.mipt.oop.file.readers.FileContentReader;
import ru.sbt.mipt.oop.file.readers.FileContentReaderImpl;
import ru.sbt.mipt.oop.file.readers.SmartHomeJsonReader;
import ru.sbt.mipt.oop.file.readers.SmartHomeReader;
import ru.sbt.mipt.oop.signaling.Signaling;

import java.util.Arrays;

@Configuration
public class DefaultConfiguration {

    @Bean
    EventProcessor handler() {
        return new CompositeHandler(
                Arrays.asList(
                        doorEventProcessor(),
                        lightEventProcessor(),
                        hallDoorEventProcessor(),
                        roomLightEventProcessor(),
                        signalingEventProcessor()
                )
        );
    }

    @Bean
    EventProcessor doorEventProcessor() {
        return new DoorEventProcessor(smartHome());
    }

    @Bean
    EventProcessor lightEventProcessor() {
        return new LightEventProcessor(smartHome());
    }

    @Bean
    EventProcessor hallDoorEventProcessor() {
        return new HallDoorEventProcessor(smartHome());
    }

    @Bean
    EventProcessor roomLightEventProcessor() {
        return new RoomLightEventProcessor(smartHome());
    }

    @Bean
    EventProcessor signalingEventProcessor() {
        return new SignalingEventProcessor(signaling());
    }

    @Bean
    Signaling signaling() {
        return new Signaling();
    }

    @Bean
    SmartHome smartHome() {
        return smartHomeReader().read("smart-home-1.js");
    }

    @Bean
    SmartHomeReader smartHomeReader() {
        return new SmartHomeJsonReader(fileContentReader());
    }

    @Bean
    FileContentReader fileContentReader() {
        return new FileContentReaderImpl();
    }
}
