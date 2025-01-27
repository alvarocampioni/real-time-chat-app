package Client;

import Server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Objects;


public class ClientHandler implements Runnable {
    private String username;
    private BufferedReader reader;
    public BufferedWriter writer;
    public Socket clientSocket;
    public Server server;

    public ClientHandler(Socket clientSocket, String username, Server server) {
        try {
            this.server = server;
            this.username = username;
            this.clientSocket = clientSocket;
            this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())) ;
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e){
            close();
            System.out.println("Error: " + e);
        }
    }

    public void close(){
        try {
            if(server.connectedClients.contains(this)) removeClient();
            if(clientSocket != null) clientSocket.close();
            if(writer != null) writer.close();
            if(reader != null) reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while(clientSocket.isConnected()) {
            try{
                messageFromClient = reader.readLine();
                if(Server.commands.contains(messageFromClient)) {
                    if (messageFromClient.equals("/online")) {
                        writer.write("-SERVER MESSAGE-");
                        writer.newLine();
                        writer.write("[");
                        for(int i = 0; i < server.connectedClients.size(); i++) {
                            writer.write(server.connectedClients.get(i).username);
                            if(i + 1 < server.connectedClients.size()) writer.write(",");
                        }
                        writer.write("]");
                        writer.newLine();
                        writer.flush();
                    }
                }
                if(messageFromClient != null) {
                    System.out.println("Reading and broadcasting: " + messageFromClient);
                    broadcastMessage(messageFromClient);
                } else {
                    close();
                    break;
                }
            } catch (IOException e){
                close();
                System.out.println("Error client " + username + ": " + e);
                break;
            }
        }
    }

    public void broadcastMessage(String message){
        for(ClientHandler client : server.connectedClients) {
            try{
                if(!(Objects.equals(client.username, username))){
                    client.writer.write(username);
                    client.writer.newLine();
                    client.writer.write(message);
                    client.writer.newLine();
                    client.writer.flush();
                }
            } catch (IOException e){
                close();
                break;
            }
        }
    }

    public void removeClient(){
        server.connectedClients.remove(this);
        close();
    }
}


