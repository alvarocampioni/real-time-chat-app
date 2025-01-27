package Client;

import Repository.ClientRepository;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    public ArrayList<Integer> ports = new ArrayList<>();
    public final String username;
    public final String password;
    public Socket socket;
    public BufferedReader reader;
    public BufferedWriter writer;
    public VBox vbox;
    public ClientRepository clientRepository = new ClientRepository();

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
        ports = clientRepository.getPortsByUsername(username);
    }

    public void setSocket(Socket socket) {
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e){
            close();
            System.out.println("Error: " + e);
        }
    }

    public void close(){
        try {
            if(socket != null) socket.close();
            if(writer != null) writer.close();
            if(reader != null) reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void sendMessage(String message){
        try{
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e){
            System.out.println("Error: " + e);
        }
    }

    public void listenMessages(){
        new Thread(() -> {
            String message;
            System.out.println("Waiting for new socket...");
            while(socket != null && socket.isConnected()){
                try {
                    System.out.println("Listening for messages...");
                    String name = reader.readLine();
                    message = reader.readLine();
                    receiveMessage(message, name);
                } catch (IOException e) {
                    close();
                    break;
                }
            }
        }).start();
    }


    public void receiveMessage(String message, String name) {
        Platform.runLater(() -> {
            Text username = new Text(name);
            if(name.equals("-SERVER MESSAGE-")) username.setFill(Paint.valueOf("blue"));
            else username.setFill(Paint.valueOf("darkgreen"));
            username.setStyle("-fx-font-weight: bold; -fx-padding: 5px; -fx-font-size: 14px;");

            Text text = new Text(message);
            text.setStyle("-fx-fill: black; -fx-font-size: 12px;");

            VBox vbox2 = new VBox(5);
            vbox2.getChildren().addAll(username, text);

            vbox2.setStyle("-fx-padding: 10px; -fx-background-color: rgb(230, 230, 230); -fx-background-radius: 20px;");

            TextFlow textFlow = new TextFlow(vbox2);
            textFlow.setPadding(new Insets(5, 10, 5, 10));

            HBox hbox = new HBox(textFlow);
            hbox.setPadding(new Insets(5, 5, 5, 10));
            hbox.setAlignment(Pos.CENTER_LEFT);

            vbox.getChildren().add(hbox);
        });
    }
}
