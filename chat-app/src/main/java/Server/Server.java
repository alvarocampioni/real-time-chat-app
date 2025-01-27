package Server;

import Client.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    private final ServerSocket serverSocket;
    public ArrayList<ClientHandler> connectedClients = new ArrayList<>();
    public static ArrayList<String> commands = new ArrayList<>();

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        commands.add("/online");
    }

    @Override
    public void run() {
        try {
            while(!serverSocket.isClosed()) {
                Socket newClient = serverSocket.accept();
                System.out.println("New client connected");
                BufferedReader reader = new BufferedReader(new InputStreamReader(newClient.getInputStream()));
                String username = reader.readLine();
                ClientHandler clientHandler = new ClientHandler(newClient, username, this);
                connectedClients.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch(IOException e){
            closeSocket();
        }
    }

    public void closeSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing server socket");
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4000);
        Server server = new Server(serverSocket);
        new Thread(server).start();
        ServerSocket serverSocket2 = new ServerSocket(4001);
        Server server2 = new Server(serverSocket2);
        new Thread(server2).start();
    }
}
