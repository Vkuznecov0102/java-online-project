package ru.itsjava.dao;

import ru.itsjava.domain.User;
import lombok.SneakyThrows;
import ru.itsjava.service.ClientRunnable;
import ru.itsjava.utils.Props;

import java.io.BufferedReader;
import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final Props props;
    private ClientRunnable clientRunnable;
    private BufferedReader bufferedReader;

    public UserDaoImpl(Props props) {
        this.props = props;
    }

    public Connection startConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    props.getValue("db.url"),
                    props.getValue("db.user"),
                    props.getValue("db.password"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return connection;
    }

    @SneakyThrows
    @Override
    public Optional<User> findByNameAndPassword(String name, String password) {
        Connection connection = startConnection();

        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("select count(*) cnt from chat_schema.users where name=? and password=?;");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int userCount = resultSet.getInt("cnt");

            if (userCount == 1) {
                return Optional.of(new User(name,password));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }



    @SneakyThrows
    @Override
    public void createUser(String name, String password) {
        Connection connection = startConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("insert into chat_schema.users(name,password) values (?,?)");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);

        preparedStatement.executeUpdate();

    }

    @SneakyThrows
    @Override
    public void deleteUser(int id){
        Connection connection = startConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("delete from chat_schema.users where id=?");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
        System.out.println("Пользователь успешно удален");
    }

    @SneakyThrows
    @Override
    public void updateUserPassword(String password,int id){
        Connection connection=startConnection();
        PreparedStatement preparedStatement= connection.prepareStatement("update chat_schema.users set password=? where id=?");
        preparedStatement.setString(1,password);
        preparedStatement.setInt(2,id);
        preparedStatement.executeUpdate();
        System.out.println("Пароль успешно изменен");
    }

    @SneakyThrows
    @Override
    public void updateUserName(String name,int id){
        Connection connection=startConnection();
        PreparedStatement preparedStatement= connection.prepareStatement("update chat_schema.users set name=? where id=?");
        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,id);
        preparedStatement.executeUpdate();
        System.out.println("Пароль успешно изменен");
    }

    static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }

    }
}
