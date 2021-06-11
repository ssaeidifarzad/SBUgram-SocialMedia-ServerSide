package Server;

import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket socket = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {

        }
    }
}
