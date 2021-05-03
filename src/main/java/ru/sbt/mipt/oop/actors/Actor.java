package ru.sbt.mipt.oop.actors;

public interface Actor extends Runnable {
    void run();

    String generateButton();
}
