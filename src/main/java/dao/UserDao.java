package dao;

import domain.User;

public interface UserDao {
    User findByNameAndPassword(String name,String password);

    void createUser(String name, String password);

    void deleteUser(int id);

    void updateUserPassword(String password,int id);

    void updateUserName(String name,int id);
}
