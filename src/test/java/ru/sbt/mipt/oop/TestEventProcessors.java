package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.event.processors.*;

import java.util.Arrays;
import java.util.Collections;

public class TestEventProcessors {

    private final SmartHome home = new SmartHome();
    private final Room kitchen = new Room(
            Arrays.asList(new Light("1", false), new Light("2", true)),
            Collections.singletonList(new Door(false, "1")),
            "kitchen"
    );
    private final Room bathroom = new Room(
            Collections.singletonList(new Light("3", true)),
            Collections.singletonList(new Door(false, "2")),
            "bathroom"
    );
    private final Room bedroom = new Room(Arrays.asList(
            new Light("4", false), new Light("5", false), new Light("6", true)),
            Collections.singletonList(new Door(true, "3")),
            "bedroom"
    );
    private final Room hall = new Room(
            Arrays.asList(new Light("7", false), new Light("8", false), new Light("9", false)),
            Collections.singletonList(new Door(true, "4")),
            "hall"
    );
    private EventProcessor processor;


    @BeforeEach
    void init() {
        home.addRoom(kitchen);
        home.addRoom(bathroom);
        home.addRoom(bedroom);
        home.addRoom(hall);
        processor = new CompositeHandler(
                Arrays.asList(
                        new LightEventProcessor(home),
                        new DoorEventProcessor(home),
                        new HallDoorEventProcessor(home)
                )
        );
    }

    @Nested
    class TestDoors {

        @Test
        void testOpenSimpleDoor() {
            processor.processEvent(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
            processor.processEvent(new SensorEvent(SensorEventType.DOOR_OPEN, "3"));

            Assertions.assertTrue(kitchen.getDoor("1").isOpen());
            Assertions.assertTrue(bedroom.getDoor("3").isOpen());

            Assertions.assertFalse(bathroom.getDoor("2").isOpen());

            Assertions.assertFalse(kitchen.getLight("1").isOn());
        }

        @Test
        void testCloseSimpleDoor() {
            processor.processEvent(new SensorEvent(SensorEventType.DOOR_CLOSED, "1"));
            processor.processEvent(new SensorEvent(SensorEventType.DOOR_CLOSED, "3"));

            Assertions.assertFalse(kitchen.getDoor("1").isOpen());
            Assertions.assertFalse(bedroom.getDoor("3").isOpen());

            Assertions.assertTrue(hall.getDoor("4").isOpen());

            Assertions.assertTrue(bathroom.getLight("3").isOn());
        }

        @Test
        void testCloseHallDoor() {
            processor.processEvent(new SensorEvent(SensorEventType.DOOR_CLOSED, "4"));

            for(Room room : home.getRooms())
                for(Light lights : room.getLights())
                    Assertions.assertFalse(lights.isOn());
        }
    }

    @Nested
    class TestLights {

        @Test
        void testLightOn() {
            processor.processEvent(new SensorEvent(SensorEventType.LIGHT_ON, "1"));
            processor.processEvent(new SensorEvent(SensorEventType.LIGHT_ON, "2"));
            processor.processEvent(new SensorEvent(SensorEventType.LIGHT_ON, "9"));

            Assertions.assertTrue(kitchen.getLight("1").isOn());
            Assertions.assertTrue(kitchen.getLight("2").isOn());
            Assertions.assertTrue(hall.getLight("9").isOn());

            Assertions.assertFalse(bedroom.getLight("4").isOn());

            Assertions.assertFalse(kitchen.getDoor("1").isOpen());
            Assertions.assertFalse(bathroom.getDoor("2").isOpen());
        }

        @Test
        void testLightOff() {
            processor.processEvent(new SensorEvent(SensorEventType.LIGHT_OFF, "2"));
            processor.processEvent(new SensorEvent(SensorEventType.LIGHT_OFF, "3"));
            processor.processEvent(new SensorEvent(SensorEventType.LIGHT_OFF, "9"));

            Assertions.assertFalse(kitchen.getLight("2").isOn());
            Assertions.assertFalse(bathroom.getLight("3").isOn());
            Assertions.assertFalse(hall.getLight("9").isOn());

            Assertions.assertTrue(bedroom.getLight("6").isOn());

            Assertions.assertFalse(bathroom.getDoor("2").isOpen());
            Assertions.assertTrue(bedroom.getDoor("3").isOpen());
        }
    }
}
