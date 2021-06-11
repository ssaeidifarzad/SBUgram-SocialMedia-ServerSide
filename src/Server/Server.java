package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    public static final int PORT = 1111;
    public static ServerSocket serverSocket = null;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connected");
                new ClientHandler(socket).run();
            } catch (IOException e) {
                System.out.println("Connection failed");
            }
        }
    }
}
