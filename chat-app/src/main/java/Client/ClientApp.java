package Client;

import Controller.ChatController;
import Service.LoginService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Chat2000");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

