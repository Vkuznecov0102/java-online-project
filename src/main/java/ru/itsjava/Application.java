package ru.itsjava;

import service.ServerService;
import service.ServerServiceImpl;

import java.io.IOException;

public class Application {


    public static void main(String[] args) throws IOException {
        ServerService serverService = new ServerServiceImpl();
        serverService.start();
    }
}
