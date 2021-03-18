package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.decorators.SignalingDecorator;
import ru.sbt.mipt.oop.event.processors.*;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.SignalingEvent;
import ru.sbt.mipt.oop.events.SignalingEventType;
import ru.sbt.mipt.oop.signaling.ActivateState;
import ru.sbt.mipt.oop.signaling.Signaling;

import java.util.Arrays;
import java.util.Collections;

public class TestSignalingDecorator {
    private final Room kitchen = new Room(
            Collections.singletonList(new Light("1", true)),
            Collections.singletonList(new Door(false, "1")),
            "kitchen"
    );
    private final String defaultCode = "aside";
    Signaling signaling;
    SignalingDecorator decorator;

    @BeforeEach
    void init() {
        SmartHome home = new SmartHome(Collections.singletonList(kitchen));
        signaling = new Signaling();
        CompositeHandler handler = new CompositeHandler(
                Arrays.asList(
                        new LightEventProcessor(home),
                        new DoorEventProcessor(home),
                        new HallDoorEventProcessor(home),
                        new SignalingEventProcessor(signaling)
                )
        );
        decorator = new SignalingDecorator(handler, signaling);
    }

    void assertOpenDoorTurnOffLight() {
        Assertions.assertTrue(kitchen.getDoor("1").isOpen());
        Assertions.assertFalse(kitchen.getLight("1").isOn());
    }

    void assertCloseDoorTurnOnLight() {
        Assertions.assertFalse(kitchen.getDoor("1").isOpen());
        Assertions.assertTrue(kitchen.getLight("1").isOn());
    }

    void OpenDoorTurnOffLight() {
        decorator.processEvent(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        decorator.processEvent(new SensorEvent(SensorEventType.LIGHT_OFF, "1"));
    }

    @Test
    void testUnActivatedSignaling() {
        OpenDoorTurnOffLight();
        assertOpenDoorTurnOffLight();
    }

    @Test
    void testActivateSignaling() {
        decorator.processEvent(new SignalingEvent(SignalingEventType.ALARM_ACTIVATE, defaultCode));
        Assertions.assertTrue(signaling.getState() instanceof ActivateState);

        OpenDoorTurnOffLight();
        assertCloseDoorTurnOnLight();
    }

    @Test
    void testDeactivateCorrectCode() {
        decorator.processEvent(new SignalingEvent(SignalingEventType.ALARM_ACTIVATE, defaultCode));
        decorator.processEvent(new SignalingEvent(SignalingEventType.ALARM_DEACTIVATE, defaultCode));

        OpenDoorTurnOffLight();
        assertOpenDoorTurnOffLight();
    }

    @Test
    void testDeactivateWrongCode() {
        String wrongCode = "loch";
        Assertions.assertNotEquals(wrongCode, defaultCode);

        decorator.processEvent(new SignalingEvent(SignalingEventType.ALARM_ACTIVATE, defaultCode));
        decorator.processEvent(new SignalingEvent(SignalingEventType.ALARM_DEACTIVATE, wrongCode));

        OpenDoorTurnOffLight();
        assertCloseDoorTurnOnLight();
    }
}
