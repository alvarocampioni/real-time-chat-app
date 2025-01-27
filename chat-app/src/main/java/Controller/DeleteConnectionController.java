package Controller;

import Repository.ServerRepository;
import Service.LoginService;
import helper.ConfirmationDialog;
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
import java.util.Objects;

public class DeleteConnectionController {

    @FXML
    private Button back;

    @FXML
    private Button delete;

    @FXML
    private TextField port;

    public ServerRepository serverRepository = new ServerRepository();

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/connections.fxml")));
        Stage stage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        try {
            if(!LoginService.logged.ports.contains(Integer.parseInt(port.getText()))) {
                new WarningDialog("Error", "Port not found");
                return;
            }
            ConfirmationDialog confirmationDialog = new ConfirmationDialog("Delete Connection", "Are you sure you want to delete this connection? All messages from you will be deleted");
            if(confirmationDialog.showDialog()){
                LoginService.logged.ports.removeIf((element) -> (element == Integer.parseInt(port.getText())));
                serverRepository.removeClientFromServer(Integer.parseInt(port.getText()), LoginService.logged.username);
            } else {
                return;
            }
        } catch (NumberFormatException e) {
            new WarningDialog("Error", "Invalid entry format: must me integer");
            return;
        }
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/connections.fxml")));
        Stage stage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}