package Repository;

import java.sql.*;
import java.util.ArrayList;

public class ServerRepository {
    Connection connection;

    public ServerRepository() {
        connection = DBConnection.getConnection();
    }

    public void addClientToServer(int port, String username, Timestamp now) {
        try{
            String query = "INSERT IGNORE INTO chatgroup(groupPort, username, joinDate) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, port);
            statement.setString(2, username);
            statement.setTimestamp(3, now);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding client to server", e);
        }
    }

    public void removeClientFromServer(int port, String username) {
        try {
            String query = "DELETE FROM chatgroup WHERE username = ? AND groupPort = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, port);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error removing client from server", e);
        }
    }

    public ArrayList<String> getMembersByPort(int port) {
        try{
            ArrayList<String> members = new ArrayList<>();
            String query = "SELECT username FROM chatgroup WHERE groupPort = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, port);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                members.add(resultSet.getString("username"));
            }
            return members;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error locating group members");
        }
    }
}
