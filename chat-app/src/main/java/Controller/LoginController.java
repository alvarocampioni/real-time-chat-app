package Controller;

import Client.Client;
import Client.ClientHandler;
import Service.LoginService;
import helper.WarningDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

@Controller
public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private Hyperlink signupLink;

    private final LoginService loginService = new LoginService();

    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {
        Client client = new Client(username.getText(), password.getText());
        if(loginService.authenticate(client)) {
            Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/connections.fxml")));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void handleLinkClick(ActionEvent event) throws Exception {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/register.fxml")));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
