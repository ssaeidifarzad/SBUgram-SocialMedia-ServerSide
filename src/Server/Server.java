package Server;

import DataBase.Database;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 1111;
    public static ServerSocket serverSocket;
    public static void main(String[] args) {
        Database.getInstance().init();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server Started");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            } catch (IOException e) {
                System.out.println("Connection failed");
            }
        }
    }

}
