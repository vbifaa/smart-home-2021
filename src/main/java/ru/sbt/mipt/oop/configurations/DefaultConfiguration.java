package ru.sbt.mipt.oop.configurations;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.SmsSender;
import ru.sbt.mipt.oop.SmsSenderImpl;
import ru.sbt.mipt.oop.adapters.EventHandlerAdapter;
import ru.sbt.mipt.oop.decorators.SignalingDecorator;
import ru.sbt.mipt.oop.event.processors.*;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.file.readers.FileContentReader;
import ru.sbt.mipt.oop.file.readers.FileContentReaderImpl;
import ru.sbt.mipt.oop.file.readers.SmartHomeJsonReader;
import ru.sbt.mipt.oop.file.readers.SmartHomeReader;
import ru.sbt.mipt.oop.signaling.Signaling;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DefaultConfiguration {

    @Bean
    SensorEventsManager defaultSensorEventsManager() {
        SensorEventsManager res = new SensorEventsManager();
        res.registerEventHandler(adapter());
        return res;
    }

    @Bean
    EventHandlerAdapter adapter() {
        return new EventHandlerAdapter(
                eventProcessorDecorator(),
                convertCCSensorEventType()
        );
    }

    @Bean
    Map<String, SensorEventType> convertCCSensorEventType() {
        return  new HashMap<String, SensorEventType>() {{
            put("LightIsOn", SensorEventType.LIGHT_ON);
            put("LightIsOff", SensorEventType.LIGHT_OFF);
            put("DoorIsOpen", SensorEventType.DOOR_OPEN);
            put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
            put("DoorIsLocked", SensorEventType.DOOR_CLOSED);
            put("DoorIsUnlocked", SensorEventType.DOOR_OPEN);
        }};
    }

    @Bean
    EventProcessor eventProcessorDecorator() {
        return new SignalingDecorator(handler(), signaling(), smsSender());
    }

    @Bean
    SmsSender smsSender() {
        return new SmsSenderImpl();
    }

    @Bean
    EventProcessor handler() {
        return new CompositeHandler(
                Arrays.asList(
                        doorEventProcessor(),
                        lightEventProcessor(),
                        hallDoorEventProcessor(),
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
