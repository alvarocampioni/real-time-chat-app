package Controller;

import Service.RegisterService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterController {

    @FXML
    private TextField password;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink signinLink;

    @FXML
    private TextField username;

    private final RegisterService registerService = new RegisterService();

    @FXML
    void handleLinkClick(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleRegisterButton(ActionEvent event) {
        registerService.register(username.getText(), password.getText());
        username.clear();
        password.clear();
    }

}
