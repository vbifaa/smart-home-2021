package ru.sbt.mipt.oop.configurations;

import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.SmsSender;
import ru.sbt.mipt.oop.SmsSenderImpl;
import ru.sbt.mipt.oop.actors.Actor;
import ru.sbt.mipt.oop.actors.ActorImpl;
import ru.sbt.mipt.oop.actors.ActorsConsumer;
import ru.sbt.mipt.oop.decorators.SignalingDecorator;
import ru.sbt.mipt.oop.event.processors.*;
import ru.sbt.mipt.oop.file.readers.SmartHomeReader;
import ru.sbt.mipt.oop.remote.controllers.ActorRemoteController;
import ru.sbt.mipt.oop.remote.controllers.commands.*;
import ru.sbt.mipt.oop.signaling.Signaling;

import java.util.Arrays;
import java.util.HashMap;

@Configuration
@ComponentScan
@Import(DefaultConfiguration.class)
public class ActorsConfiguration {

    @Autowired
    @Qualifier("smartHomeReader")
    private SmartHomeReader smartHomeReader;

    @Bean
    ActorsConsumer consumer() {
        ActorRemoteController childController = childRemoteController();
        ActorRemoteController parentController = parentRemoteController();

        childController.addActor(parentController);
        parentController.addActor(childController);

        Actor child = new ActorImpl(childController);
        Actor parent = new ActorImpl(parentController);

        return new ActorsConsumer(Arrays.asList(child, parent));
    }

    @Bean
    ActorRemoteController parentRemoteController() {
        return new ActorRemoteController(new HashMap<String, Command>(){{
            put("A", activateSignaling());
            put("B", deactivateSignaling());
            put("C", setAlarmSignaling());
            put("D", turnOffAllLight());
            put("1", turnOnAllLight());
            put("2", turnOnLightInHall());
            put("3", closeHallDoor());
        }});
    }

    @Bean
    ActorRemoteController childRemoteController() {
        return new ActorRemoteController(new HashMap<String, Command>(){{
            put("A", closeDoor());
            put("B", openDoor());
            put("C", turnOffLightInRoom());
            put("D", turnOnLightInRoom());
            put("1", setAlarmSignaling());
        }});
    }

    /**
     * not Bean because every actor has his own home, eventProcessors, etc.
     */

    Command deactivateSignaling() {
        return new DeactivateSignaling(signalingDecorator(), "ummu");
    }

    Command turnOnLightInRoom() {
        return new TurnOnLightInRoom(signalingDecorator(), "bathroom");
    }

    Command turnOffLightInRoom() {
        return new TurnOffLightInRoom(signalingDecorator(), "bathroom");
    }

    Command openDoor() {
        return new OpenDoor(signalingDecorator(), "2");
    }

    Command closeDoor() {
        return new CloseDoor(signalingDecorator(), "2");
    }

    Command activateSignaling() {
        return new ActivateSignaling(signalingDecorator(),"ummu");
    }

    Command closeHallDoor() {
        return new CloseHallDoor(signalingDecorator(), "1");
    }

    Command setAlarmSignaling() {
        return new SetAlarmSignaling(signalingDecorator());
    }

    Command turnOffAllLight() {
        return new TurnOnAllLight(signalingDecorator());
    }

    Command turnOnAllLight() {
        return new TurnOnAllLight(signalingDecorator());
    }

    Command turnOnLightInHall() {
        return new TurnOnLightInHall(signalingDecorator());
    }

    EventProcessor signalingDecorator() {
        return new SignalingDecorator(handler(), signaling(), smsSender());
    }

    SmsSender smsSender() {
        return new SmsSenderImpl();
    }

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

    EventProcessor doorEventProcessor() {
        return new DoorEventProcessor(smartHome());
    }

    EventProcessor lightEventProcessor() {
        return new LightEventProcessor(smartHome());
    }

    EventProcessor hallDoorEventProcessor() {
        return new HallDoorEventProcessor(smartHome());
    }

    EventProcessor roomLightEventProcessor() {
        return new RoomLightEventProcessor(smartHome());
    }

    EventProcessor signalingEventProcessor() {
        return new SignalingEventProcessor(signaling());
    }

    Signaling signaling() {
        return new Signaling();
    }

    SmartHome smartHome() {
        return smartHomeReader.read("smart-home-1.js");
    }
}
