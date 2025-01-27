package Repository;

import java.sql.*;
import java.util.ArrayList;

public class MessagesRepository {
    private final Connection connection;

    public MessagesRepository() {
        connection = DBConnection.getConnection();
    }

    public ArrayList<Message> getMessagesByPortAndName(int port, String username) {
        try{
            ArrayList<Message> messages = new ArrayList<>();
            //get the date the user joined
            String queryTime = "SELECT joinDate FROM chatgroup WHERE groupPort = ? AND username = ?";
            PreparedStatement statement = connection.prepareStatement(queryTime);
            statement.setInt(1, port);
            statement.setString(2, username);
            ResultSet resultSet = statement.executeQuery();
            Timestamp joinDate = new Timestamp(System.currentTimeMillis());
            if(resultSet.next()) {
                joinDate = resultSet.getTimestamp("joinDate");
            }
            //get the messages from after the user joined
            String query = "SELECT * FROM messagessent WHERE groupPort = ? AND instant > ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, port);
            statement.setTimestamp(2, joinDate);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(new Message(resultSet.getString("content"), resultSet.getString("sentBy"), resultSet.getTimestamp("instant")));
            }
            return messages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addMessage(Message message, int port) {
        try {
            String query = "INSERT INTO messagessent(groupPort, sentBy, content, instant) VALUES(?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, port);
            statement.setString(2, message.sentBy());
            statement.setString(3, message.content());
            statement.setTimestamp(4, message.sentDate());
            statement.execute();
            System.out.println("Message added");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
