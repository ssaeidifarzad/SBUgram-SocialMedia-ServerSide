package Server;

import DataBase.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static final int PORT = 1111;
    public static ServerSocket serverSocket;

    public static void main(String[] args) {
        Database.init();
        Scanner scanner = new Scanner(System.in);
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
                if (scanner.next().equals("1"))
                    break;
            } catch (IOException e) {
                System.out.println("Connection failed");
            }
        }
        Database.update();
    }

}
