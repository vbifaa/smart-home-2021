package ru.sbt.mipt.oop.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rc.RemoteControl;
import rc.RemoteControlRegistry;
import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.remote.controllers.DefaultRemoteController;
import ru.sbt.mipt.oop.remote.controllers.commands.*;

import java.util.HashMap;

@Configuration
@ComponentScan
@Import(SignalingDecoratorConfiguration.class)
public class RemoteControlConfiguration {

    @Autowired
    @Qualifier("signalingDecorator")
    private EventProcessor processor;

    @Bean
    RemoteControlRegistry remoteControlRegistry() {
        RemoteControlRegistry rc = new RemoteControlRegistry();
        rc.registerRemoteControl(remoteControl(), "1");
        return rc;
    }

    @Bean
    RemoteControl remoteControl() {
        return new DefaultRemoteController(new HashMap<String, Command>(){{
            put("A", activateSignaling());
            put("B", closeHallDoor());
            put("C", setAlarmSignaling());
            put("D", turnOffAllLight());
            put("1", turnOnAllLight());
            put("2", turnOnLightInHall());
        }});
    }

    @Bean
    Command activateSignaling() {
        return new ActivateSignaling(processor,"ummu");
    }

    @Bean
    Command closeHallDoor() {
        return new CloseHallDoor(processor, "1");
    }

    @Bean
    Command setAlarmSignaling() {
        return new SetAlarmSignaling(processor);
    }

    @Bean
    Command turnOffAllLight() {
        return new TurnOnAllLight(processor);
    }

    @Bean
    Command turnOnAllLight() {
        return new TurnOnAllLight(processor);
    }

    @Bean
    Command turnOnLightInHall() {
        return new TurnOnLightInHall(processor);
    }
}
