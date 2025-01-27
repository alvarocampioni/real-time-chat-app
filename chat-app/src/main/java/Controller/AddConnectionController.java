package Controller;

import Client.ClientHandler;
import Repository.ClientRepository;
import Repository.ServerRepository;
import Service.LoginService;
import helper.WarningDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

public class AddConnectionController {

    @FXML
    private Button connect;

    @FXML
    private TextField port;

    @FXML
    private Button back;

    public ServerRepository serverRepository = new ServerRepository();

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        try {
            if(port.getText().isEmpty() || Integer.parseInt(port.getText()) > 65535){
                new WarningDialog("Error", "Invalid port");
                return;
            }
            LoginService.logged.ports.add(Integer.valueOf(port.getText()));
            Timestamp now = new Timestamp(System.currentTimeMillis());
            serverRepository.addClientToServer(Integer.parseInt(port.getText()), LoginService.logged.username, now);
        } catch (NumberFormatException e) {
            new WarningDialog("Error", "Invalid entry format: must me integer");
            return;
        }
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/connections.fxml")));
        Stage stage = (Stage) connect.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/connections.fxml")));
        Stage stage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}