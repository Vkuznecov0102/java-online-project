package ru.itsjava.service;

import ru.itsjava.dao.UserDao;
import ru.itsjava.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientRunnable implements Runnable, Observer {

    private final Socket socket;
    private final ServerService serverService;
    private User user;
    private final UserDao userDao;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Client connected!");

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        String messageFromClient;

        if(authorization(bufferedReader)){
            serverService.addObserver(this);
            String welcome="В чат вошел пользователь "+ user.getName()+", приветствуем его! ";
            System.out.println(welcome);
            try {
                while (true) {
                    messageFromClient = bufferedReader.readLine();
                    if (messageFromClient == null) System.exit(0);
                    System.out.println(user.getName() + " : " + messageFromClient);
                    serverService.notifyObservers(user.getName() + " : " + messageFromClient);
                }
            }
            catch(SocketException socketException){
                socketException.printStackTrace();
            }
        }
    }

    @SneakyThrows
    public boolean authorization(BufferedReader bufferedReader) {
        String authorizationMessage;
        while (true) {
            authorizationMessage = bufferedReader.readLine();
            if (authorizationMessage == null) break;
            if (authorizationMessage.startsWith("!autho!")) {
                String login = authorizationMessage.substring(7).split(":")[0];
                String password = authorizationMessage.substring(7).split(":")[1];
                Optional<User> optionalUser = userDao.findByNameAndPassword(login, password);
                if(optionalUser.isPresent()){
                    user=optionalUser.get();
                    return true;
                }
            }
            else{
                System.out.println("Неправильные логин и пароль");
            }
        }
        return false;
    }

    @SneakyThrows
    public void registration(BufferedReader bufferedReader) {
        System.out.println("Это регистрация. Введите будущий логин.");
        String registrationMessage;
        while (true) {
            registrationMessage = bufferedReader.readLine();
            if (registrationMessage == null) break;
            System.out.println("Будущий логин");
            if (registrationMessage.startsWith("Будущий")) {
                String login = registrationMessage.substring(5).split(" ")[0];
                System.out.println("Новый пароль");
                String password = registrationMessage.substring(5).split(" ")[1];

                userDao.createUser(login, password);

            }
        }
    }

    @SneakyThrows
    @Override
    public void notifyMe(String message) {
        PrintWriter clientWriter = new PrintWriter(socket.getOutputStream());
        clientWriter.println(message);
        clientWriter.flush();
    }
}
