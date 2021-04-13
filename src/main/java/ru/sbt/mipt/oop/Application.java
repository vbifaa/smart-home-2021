package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import ru.sbt.mipt.oop.configurations.LibraryConfiguration;


public class Application {

    public static void main(String... args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
                LibraryConfiguration.class
        );
        SensorEventsManager manager = context.getBean(SensorEventsManager.class);
        manager.start();
    }

}
