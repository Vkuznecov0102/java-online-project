package ru.itsjava;


import ru.itsjava.service.ServerService;
import ru.itsjava.service.ServerServiceImpl;

public class Application {


    public static void main(String[] args) {
        ServerService serverService = new ServerServiceImpl();
        serverService.start();

//        Props props=new Props();
//        System.out.println(props.getValue("db.url"));

//        UserDao userDao=new UserDaoImpl(new Props());
//        System.out.println("userDao.findByNameAndPassword(\"U1\",\"P1\") = " + userDao.findByNameAndPassword("U1", "P1"));

//        UserDao userDao=new UserDaoImpl(new Props());
//        userDao.add("U3","P3");
//        System.out.println(userDao.findByNameAndPassword("U3", "P3"));

//        MessageDao messageDao=new MessageDaoImpl(new UserDaoImpl(new Props()));
//        messageDao.findMessageByUserId(1);

    }
}
