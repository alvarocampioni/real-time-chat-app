package Controller;

import Client.Client;
import Repository.ClientRepository;
import Repository.Message;
import Repository.MessagesRepository;
import Repository.ServerRepository;
import Service.LoginService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class ChatController implements Initializable {

    public Client client;

    @FXML
    public Text participants;

    @FXML
    public Text username;

    @FXML
    public TextField message;

    @FXML
    public Button submit;

    @FXML
    public VBox vbox;

    @FXML
    public VBox online;

    @FXML
    public TableView<?> table;

    @FXML
    public ScrollPane scrollpane;

    @FXML
    public ScrollPane scrollMembers;

    @FXML
    public Button refresh;

    @FXML
    public Button leave;

    private final MessagesRepository messagesRepository = new MessagesRepository();
    private final ServerRepository serverRepository = new ServerRepository();

    @FXML
    public void handleMessageSubmit(ActionEvent event) {
        if (!message.getText().isEmpty()) {
            client = LoginService.logged;
            client.sendMessage(message.getText());
            long time = System.currentTimeMillis();
            Timestamp now = new Timestamp(time);
            messagesRepository.addMessage(new Message(message.getText(), client.username, now), client.socket.getPort());
            send(message.getText());
            message.clear();
        }
    }

    @FXML
    public void handleRefresh(ActionEvent event) {
        loadPreviousMessages(LoginService.logged.socket.getPort());
    }

    @FXML
    public void handleLeaveChat(ActionEvent event) throws IOException {
        LoginService.logged.socket.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/connections.fxml")));
        Stage stage = (Stage) vbox.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadPreviousMessages(int port) {
        String username = LoginService.logged.username;
        ArrayList<Message> messages = messagesRepository.getMessagesByPortAndName(port, username);
        vbox.getChildren().clear();
        for (Message message : messages) {
            if (Objects.equals(message.sentBy(), username)) {
                send(message.content());
            } else {
                receive(message.content(), message.sentBy());
            }
        }
        setMembers();
    }

    public void send(String message) {
        Text text = new Text(message);
        text.setStyle("-fx-font-size: 12px; -fx-fill: white;");

        TextFlow textFlow = new TextFlow(text);
        textFlow.setTextAlignment(TextAlignment.RIGHT);
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        textFlow.setStyle("-fx-background-color: rgb(15, 125, 242); -fx-background-radius: 20px;");

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 10));
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.getChildren().add(textFlow);

        System.out.println(vbox.getChildren().size());
        vbox.getChildren().addAll(hbox);

        vbox.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollpane.setVvalue(1.0);
        });

        scrollpane.setFitToHeight(true);
        scrollpane.setFitToWidth(true);
        scrollpane.setContent(vbox);
    }

    public void receive(String content, String sentBy) {
        Text username = new Text(sentBy);
        username.setFill(Paint.valueOf("darkgreen"));
        username.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 5px;");

        Text text = new Text(content);
        text.setStyle("-fx-font-size: 12px; -fx-fill: black;");

        VBox vbox2 = new VBox(5);
        vbox2.getChildren().addAll(username, text);

        vbox2.setStyle("-fx-background-color: rgb(230, 230, 230); -fx-background-radius: 20px; -fx-padding: 10px;");

        TextFlow textFlow = new TextFlow(vbox2);
        textFlow.setPadding(new Insets(5, 10, 5, 10));

        HBox hbox = new HBox(textFlow);
        hbox.setPadding(new Insets(5, 5, 5, 10));
        hbox.setAlignment(Pos.CENTER_LEFT);

        vbox.getChildren().add(hbox);
    }

    private static StackPane getStackPaneImage(String path) {
        ImageView image = new ImageView(path);

        image.setFitHeight(500);
        image.setFitWidth(500);
        image.setPreserveRatio(true);
        Rectangle clip = new Rectangle(image.getBoundsInLocal().getMaxX(), image.getBoundsInLocal().getMaxY());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        image.setClip(clip);

        StackPane stackPane = new StackPane(image);

        stackPane.setPadding(new Insets(5, 10, 5, 10));
        stackPane.setStyle("-fx-background-color: rgb(15, 125, 242); -fx-background-radius: 20px;");
        return stackPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginService.logged.listenMessages();
        LoginService.logged.vbox = vbox;
        loadPreviousMessages(LoginService.logged.socket.getPort());
    }

    public void setMembers() {
        ArrayList<String> members = serverRepository.getMembersByPort(LoginService.logged.socket.getPort());
        online.getChildren().clear();
        for (String member : members) {
            Text text = new Text(member);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(5, 10, 5, 10));

            HBox hbox = new HBox(textFlow);
            hbox.setPadding(new Insets(5, 5, 5, 10));
            hbox.setAlignment(Pos.CENTER);

            online.getChildren().add(hbox);
        }
        scrollMembers.setFitToHeight(true);
        scrollMembers.setFitToWidth(true);
        scrollMembers.setContent(online);
    }
}


 /* TODO: Allow file sending~
    @FXML
    public Button files;
    @FXML
    public void handleFileSelection(ActionEvent event) {
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File("."));
            int result = chooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String path = file.getAbsolutePath();
                System.out.println(path);
                StackPane stackPane = getStackPaneImage(path);
                stackPane.setOnMouseClicked(mouseEvent -> {
                    Dialog<ImageView> dialog = new Dialog<>();
                    dialog.setTitle("Image Preview");
                    ImageView imageView = new ImageView(path);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(1280);
                    imageView.setFitHeight(720);
                    dialog.getDialogPane().setContent(imageView);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                    dialog.showAndWait();
                });

                HBox hbox = new HBox();
                hbox.setPadding(new Insets(5, 5, 5, 10));
                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.getChildren().add(stackPane);
                vbox.getChildren().add(hbox);
                vbox.heightProperty().addListener((obs, oldVal, newVal) -> {
                    scrollpane.setVvalue(1.0);
                });
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
 */
