package ru.sbt.mipt.oop.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.sbt.mipt.oop.SmsSender;
import ru.sbt.mipt.oop.SmsSenderImpl;
import ru.sbt.mipt.oop.decorators.SignalingDecorator;
import ru.sbt.mipt.oop.event.processors.EventProcessor;
import ru.sbt.mipt.oop.signaling.Signaling;

@Configuration
@ComponentScan
@Import(DefaultConfiguration.class)
public class SignalingDecoratorConfiguration {

    @Autowired
    @Qualifier("handler")
    private EventProcessor handler;

    @Autowired
    @Qualifier("signaling")
    private Signaling signaling;

    @Bean
    EventProcessor signalingDecorator() {
        return new SignalingDecorator(handler, signaling, smsSender());
    }

    @Bean
    SmsSender smsSender() {
        return new SmsSenderImpl();
    }
}
