package service;

import dao.UserDao;
import dao.UserDaoImpl;
import lombok.SneakyThrows;
import utils.Props;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerServiceImpl implements ServerService {

    public final static int PORT = 8001;
    public final List<Observer> observers = new ArrayList<>();
    private final UserDao userDao=new UserDaoImpl(new Props());

    @SneakyThrows
    @Override
    public void start() {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("-------Server starts--------");

        while (true) {
            Socket socket = serverSocket.accept();

            if (socket != null) {
                Thread thread = new Thread(new ClientRunnable(socket, this,userDao));
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
    public void notifyObserver(String message) {
        for (Observer observer : observers) {
            observer.notifyMe(message);
        }
    }

    @Override
    public void notifyObserverExpectMe(String message){

    }
}
