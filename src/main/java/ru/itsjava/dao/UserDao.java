package ru.itsjava.dao;

import ru.itsjava.domain.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByNameAndPassword(String name, String password);

    void createUser(String name, String password);

    void deleteUser(int id);

    void updateUserPassword(String password,int id);

    void updateUserName(String name,int id);
}
