package ru.itsjava.dao;

public interface MessageDao {

    void findMessageByUserId(int id);

    void deleteMessageById(int id);

    void updateMessage(String content,int id);

    void giveLastFifteenMessages(int id);


}
