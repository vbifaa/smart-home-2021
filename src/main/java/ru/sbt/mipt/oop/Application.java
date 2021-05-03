package ru.sbt.mipt.oop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import ru.sbt.mipt.oop.actors.ActorsConsumer;
import ru.sbt.mipt.oop.configurations.ActorsConfiguration;


public class Application {

    public static void main(String... args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
                ActorsConfiguration.class
        );
        ActorsConsumer consumer = context.getBean(ActorsConsumer.class);
        consumer.start();
    }

}
