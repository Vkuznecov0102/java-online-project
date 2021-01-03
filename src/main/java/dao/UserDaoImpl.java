package dao;

import domain.User;
import lombok.AllArgsConstructor;
import utils.Props;

import java.sql.*;

@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private final Props props;

    @Override
    public User findByNameAndPassword(String name, String password) {
        try(Connection connection= DriverManager.getConnection(
                props.getValue("db.url"),
                props.getValue("db.user"),
                props.getValue("db.password"))
        ){
            PreparedStatement preparedStatement=connection
                    .prepareStatement("select count(*) cnt from chat_schema.users where name=? and password=?;");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);

            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();

            int userCount=resultSet.getInt("cnt");

            if(userCount==1){
                return new User(name,password);
            }

        }
        catch(SQLException exception){
            exception.printStackTrace();
        }


        throw new UserNotFoundException("User not found");
    }

    @Override
    public void createUser(String name, String password) {
        try(Connection connection= DriverManager.getConnection(
                props.getValue("db.url"),
                props.getValue("db.user"),
                props.getValue("db.password"))
        ){
            PreparedStatement preparedStatement=connection
                    .prepareStatement("insert into chat_schema.users(name,password) values (?,?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);

            preparedStatement.executeUpdate();

        }
        catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(String message) {
            super(message);
        }

    }
    }

