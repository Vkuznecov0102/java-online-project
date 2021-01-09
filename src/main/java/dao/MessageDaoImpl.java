package dao;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class MessageDaoImpl implements MessageDao {

    private final UserDaoImpl userDaoImpl;

    public MessageDaoImpl(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    @SneakyThrows
    public void findMessageByUserId(int id) {
        Connection connection = userDaoImpl.startConnection();

            PreparedStatement preparedStatement = connection
                    .prepareStatement("select content from chat_schema.messages where user_id=?;");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
    }


    @Override
    @SneakyThrows
    public void deleteMessageById(int id) {
        Connection connection = userDaoImpl.startConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("delete from chat_schema.messages where id=?");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
        System.out.println("Сообщение успешно удалено");
    }

    @Override
    @SneakyThrows
    public void updateMessage(String content,int id) {
        Connection connection = userDaoImpl.startConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("update chat_schema.messages set content=? where id=?");
        preparedStatement.setString(1,content);
        preparedStatement.setInt(2,id);
        preparedStatement.executeUpdate();
    }

    @Override
    @SneakyThrows
    public void giveLastFifteenMessages(int id) {
        Connection connection = userDaoImpl.startConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("select content from chat_schema.messages where user_id=? limit 15;");
        preparedStatement.setInt(1,id);
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getInt(1));
        }

    }
}
