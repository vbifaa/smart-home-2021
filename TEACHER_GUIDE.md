За основу избавления от цепочеков if-ов была взята концепция из Python. Как например в классе LightEventProcessor:
```Java
private final HashMap<SensorEventType,Boolean> turnOnOff =
            new HashMap<SensorEventType, Boolean>(){{
                put(SensorEventType.LIGHT_OFF, false);
                put(SensorEventType.LIGHT_ON, true);
            }};
```
Т.е. создаётся словарь и по ключу берётся значение вместо if-ов.<br />
Ещё хотелось бы написать про преимущество испозования этой концепции здесь в классе EventProcessorImpl:
```Java
public EventProcessorImpl(SmartHome home) {
        this.eventProcessors =
                new HashMap<EventProcessorType, EventProcessor>() {{
                    put(EventProcessorType.LIGHT, new LightEventProcessor(home, new CommandSenderImpl()));
                    put(EventProcessorType.DOOR, new DoorEventProcessor(home));
                }};
    }
```
Здесь классы DoorEventProcessor и LightEventProcessor создаются единожды в процессе работы нашего приложения.<br />
Но у меня проблема с названиями, я так и не придумал хорошее название для этих словарей.
