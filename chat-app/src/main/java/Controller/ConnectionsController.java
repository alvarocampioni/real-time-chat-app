package Controller;

import Client.Client;
import Client.ClientHandler;
import Repository.ServerRepository;
import Service.LoginService;
import helper.WarningDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConnectionsController implements Initializable {

    @FXML
    private Button addconnection;

    @FXML
    private Button deleteconnection;

    @FXML
    private VBox vbox;

    @FXML
    private ScrollPane scroll;

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/addconnection.fxml")));
        Stage stage = (Stage) addconnection.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleDeleteConnection(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/deleteconnection.fxml")));
        Stage stage = (Stage) addconnection.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Integer> ports = LoginService.logged.ports;
        for (int port : ports) {
            Text text = new Text("PORT: " + port);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setTextAlignment(TextAlignment.LEFT);
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            textFlow.setStyle("-fx-background-color: white;" + "-fx-border-color: black;");
            textFlow.setOnMouseClicked(event -> {
                connect(port);
            });
            vbox.getChildren().addAll(textFlow);
            scroll.setFitToHeight(true);
            scroll.setFitToWidth(true);
            scroll.setContent(vbox);
        }
    }

    public void connect(int port){
        try {
            Client logged = LoginService.logged;
            logged.setSocket(new Socket("localhost",port));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(LoginService.logged.socket.getOutputStream()));
            String username = LoginService.logged.username;
            writer.write(username);
            writer.newLine();
            writer.flush();

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/chat.fxml")));
            Parent root= loader.load();
            Stage stage = (Stage) addconnection.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new WarningDialog("Error", "Could not connect to server with port: " + port);
        }
    }
}
