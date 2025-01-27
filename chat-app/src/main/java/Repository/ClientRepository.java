package Repository;

import java.sql.*;
import java.util.ArrayList;

public class ClientRepository {
    private final Connection connection;

    public ClientRepository() {
        connection = DBConnection.getConnection();
    }

    public void registerClient(String username, String password){
        try{
            String query = "INSERT INTO users(username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Client username already exists");
        }
    }

    public String getPasswordByUsername(String username) {
        try{
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("password");
            }
            return "";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error locating sign in data");
        }
    }

    public ArrayList<Integer> getPortsByUsername(String username){
        try{
            ArrayList<Integer> ports = new ArrayList<>();
            String query = "SELECT groupPort FROM chatgroup WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt("groupPort"));
                ports.add(resultSet.getInt("groupPort"));
            }
            return ports;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error locating sign in data");
        }
    }
}
