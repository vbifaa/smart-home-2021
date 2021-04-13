package ru.sbt.mipt.oop.configurations;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.sbt.mipt.oop.adapters.EventHandlerAdapter;
import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.events.SensorEventType;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
@Import(SignalingDecoratorConfiguration.class)
public class LibraryConfiguration {

    @Autowired
    @Qualifier("signalingDecorator")
    private EventProcessor processor;

    @Bean
    SensorEventsManager defaultSensorEventsManager() {
        SensorEventsManager res = new SensorEventsManager();
        res.registerEventHandler(adapter());
        return res;
    }

    @Bean
    EventHandlerAdapter adapter() {
        return new EventHandlerAdapter(
                processor,
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
}
