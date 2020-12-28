package ru.itsjava;

import service.ServerService;
import service.ServerServiceImpl;

public class Application {


    public static void main(String[] args) {
        ServerService serverService = new ServerServiceImpl();
        serverService.start();
    }
}
