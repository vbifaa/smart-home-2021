package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rc.RemoteControl;
import ru.sbt.mipt.oop.decorators.SignalingDecorator;
import ru.sbt.mipt.oop.event.processors.*;
import ru.sbt.mipt.oop.remote.controllers.DefaultRemoteController;
import ru.sbt.mipt.oop.remote.controllers.commands.*;
import ru.sbt.mipt.oop.signaling.Signaling;

import java.util.Arrays;
import java.util.Collections;

public class TestRemoteControl {
    private final RemoteControl controller = new DefaultRemoteController();
    private EventProcessor processor;
    private final SmartHome home = new SmartHome();
    private final String hallDoorId = "1";
    private final Room hall = new Room(
            Arrays.asList(
                    new Light("1", true),
                    new Light("2", true),
                    new Light("3", false)
            ),
            Collections.singletonList(new Door(true, hallDoorId)),
            "hall"
    );
    private final Signaling signaling = new Signaling();

    private final String turnOffAllLights = "A";
    private final String closeHallDoor = "B";
    private final String turnOnLightInHall = "C";
    private final String activateSignaling = "D";
    private final String setAlarmSignaling = "1";
    private final String turnOnAllLights = "2";

    @BeforeEach
    void init() {
        initHome();
        initEventProcessor();
        initCommands();
    }

    void initHome() {
        home.addRoom(hall);
        home.addRoom(
                new Room(
                        Arrays.asList(
                            new Light("4", false),
                            new Light("5", false),
                            new Light("6", true)
                        ),
                        Collections.singletonList(new Door(true, "3")),
                        "bedroom"
                )
        );
    }

    void initEventProcessor() {
        EventProcessor handler = new CompositeHandler(
                Arrays.asList(
                        new DoorEventProcessor(home),
                        new LightEventProcessor(home),
                        new HallDoorEventProcessor(home),
                        new RoomLightEventProcessor(home),
                        new SignalingEventProcessor(signaling)
                )
        );
        processor = new SignalingDecorator(
                handler,
                signaling,
                new SmsSenderImpl()
        );
    }

    void initCommands() {
        Command command = new TurnOffAllLight(processor);
        ((DefaultRemoteController) controller).registerButton(turnOffAllLights, command);

        command = new CloseHallDoor(processor, hallDoorId);
        ((DefaultRemoteController) controller).registerButton(closeHallDoor, command);

        command = new TurnOnLightInHall(processor);
        ((DefaultRemoteController) controller).registerButton(turnOnLightInHall, command);

        command = new ActivateSignaling(processor, "clock");
        ((DefaultRemoteController) controller).registerButton(activateSignaling, command);

        command = new SetAlarmSignaling(processor);
        ((DefaultRemoteController) controller).registerButton(setAlarmSignaling, command);

        command = new TurnOnAllLight(processor);
        ((DefaultRemoteController) controller).registerButton(turnOnAllLights, command);
    }

    void assertAllLights(boolean isOn) {
        for(Room room : home.rooms)
            for(Light light : room.getLights())
                Assertions.assertEquals(isOn, light.isOn());
    }

    void assertCloseHallDoor() {
        Assertions.assertFalse(hall.getDoor(hallDoorId).isOpen());
        assertAllLights(false);
    }

    void assertTurnOnLightInHall() {
        for(Light light : hall.getLights())
            Assertions.assertTrue(light.isOn());
    }

    @Test
    void testTurnOffAllLights() {
        controller.onButtonPressed(turnOffAllLights, "");
        assertAllLights(false);
    }

    @Test
    void testCloseHallDoor() {
        controller.onButtonPressed(closeHallDoor, "");
        assertCloseHallDoor();
    }

    @Test
    void testTurnOnLightInHall() {
        controller.onButtonPressed(turnOnLightInHall, "");
        assertTurnOnLightInHall();
    }

    @Test
    void testActivateSignaling() {
        controller.onButtonPressed(activateSignaling, "");
        Assertions.assertTrue(signaling.isActivate());
    }

    @Test
    void testSetAlarmSignaling() {
        controller.onButtonPressed(setAlarmSignaling, "");
        Assertions.assertTrue(signaling.isAlarm());
    }

    @Test
    void testTurnOnAllLight() {
        controller.onButtonPressed(turnOnAllLights, "");
        assertAllLights(true);
    }

    @Test
    void testAll() {
        controller.onButtonPressed(turnOffAllLights, "");
        assertAllLights(false);

        controller.onButtonPressed(turnOnAllLights, "");
        assertAllLights(true);

        controller.onButtonPressed(closeHallDoor, "");
        assertCloseHallDoor();

        controller.onButtonPressed(turnOnLightInHall, "");
        assertTurnOnLightInHall();

        controller.onButtonPressed(activateSignaling, "");
        Assertions.assertTrue(signaling.isActivate());

        controller.onButtonPressed(setAlarmSignaling, "");
        Assertions.assertTrue(signaling.isAlarm());
    }
}
