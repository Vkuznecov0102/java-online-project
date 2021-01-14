package ru.itsjava.service;

public interface Observable {
    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void notifyObservers(String message);

    void notifyObserversExpectOne(String message,Observer one);
}
