package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.signaling.ActivateState;
import ru.sbt.mipt.oop.signaling.AlarmState;
import ru.sbt.mipt.oop.signaling.DeactivateState;
import ru.sbt.mipt.oop.signaling.Signaling;

public class TestSignaling {
    private Signaling signaling;
    private final String defaultPassword = "qwerty";

    @BeforeEach
    void init() {
        signaling = new Signaling();
    }

    @Test
    void testSignalingHasDeactivateState() {
        Assertions.assertTrue(signaling.isDeactivate());
    }

    @Test
    void testActivate() {
        signaling.activate(defaultPassword);
        Assertions.assertTrue(signaling.isActivate());
    }

    @Test
    void testDeactivateCorrectPassword() {
        signaling.activate(defaultPassword);
        signaling.deactivate(defaultPassword);

        Assertions.assertTrue(signaling.isDeactivate());
    }

    @Test
    void testDeactivateWrongPassword() {
        String wrongPassword = "1234";
        Assertions.assertNotEquals(defaultPassword, wrongPassword);

        signaling.activate(defaultPassword);
        signaling.deactivate(wrongPassword);

        Assertions.assertTrue(signaling.isAlarm());
    }

    @Test
    void testCantSetNewPasswordWhenActivate() {
        String newPassword = "1234";
        Assertions.assertNotEquals(defaultPassword, newPassword);

        signaling.activate(defaultPassword);
        signaling.activate(newPassword);
        signaling.deactivate(newPassword);

        Assertions.assertTrue(signaling.isAlarm());
    }
}
