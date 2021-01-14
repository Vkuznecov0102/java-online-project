package ru.itsjava.service;

import ru.itsjava.dao.UserDao;
import ru.itsjava.dao.UserDaoImpl;
import lombok.SneakyThrows;
import ru.itsjava.utils.Props;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerServiceImpl implements ServerService, Observable {

    public final static int PORT = 8001;
    public final List<Observer> observers = new ArrayList<>();
    private final UserDao userDao = new UserDaoImpl(new Props());

    @SneakyThrows
    @Override
    public void start() {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("-------Server starts--------");

        while (true) {
            Socket socket = serverSocket.accept();

            if (socket != null) {
                Thread thread = new Thread(new ClientRunnable(socket, this, userDao));
                thread.start();
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.notifyMe(message);
        }
    }

    @Override
    public void notifyObserversExpectOne(String message, Observer one) {
        for (Observer observer : observers) {
            if (!observer.equals(one)) {
                observer.notifyMe(message);
            }
        }
    }
}
