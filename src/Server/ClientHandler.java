package Server;

import DataBase.Database;
import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.*;
import Model.Messages.ServerMessages.LoginResponse;
import Model.Messages.ServerMessages.ServerMessage;
import Model.Messages.ServerMessages.SignupResponse;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public class ClientHandler implements Runnable {
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    User user;
    public static final String BIRTHDATE_FORMAT_REGEX = "(19|20)[0-9]{2}/(1[0-2]|[1-9])/([1-9]|[1-2][0-9]|3[0-1])";

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ClientMessage message = null;
        while (!(message instanceof ExitMessage)) {
            try {
                message = ((ClientMessage) objectInputStream.readObject());
                if (message instanceof LoginRequest) {
                    login((LoginRequest) message);
                } else if (message instanceof SignupRequest) {
                    signup(((SignupRequest) message));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (user != null) {
            System.out.println("[ action: quit\n" +
                    "\"" + user.getUsername() + "\" quited\n" +
                    "time: " + LocalDateTime.now() + " ]"
            );
        }
        disconnect();
    }

    private void disconnect() {
        try {
            socket.close();
            objectInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(LoginRequest lr) {
        Map<String, String> ld = Database.getInstance().getLoginData();
        LoginResponse loginResponse = new LoginResponse();
        if (ld.containsKey(lr.getUsername())) {
            if (ld.get(lr.getUsername()).equals(lr.getPassword())) {
                loginResponse.addResponse("success");
                user = Database.getInstance().getUser(lr.getUsername());
                loginResponse.setUser(user);
                System.out.println("[ action: login\n" +
                        "\"" + user.getUsername() + "\" login\n" +
                        "time: " + LocalDateTime.now() + " ]"
                );
            } else {
                loginResponse.addResponse("wrong_password");
            }
        } else {
            loginResponse.addResponse("no_username");
        }
        try {
            sendResponse(loginResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signup(SignupRequest signupRequest) {
        SignupResponse signupResponse = new SignupResponse();
        boolean signup = true;
        Map<String, String> ld = Database.getInstance().getLoginData();
        if (ld.containsKey(signupRequest.getUsername())) {
            signupResponse.addResponse("unavailable_username");
            signup = false;
        }
        if (signupRequest.getPassword().length() < 8) {
            signupResponse.addResponse("wrong_password_format");
            signup = false;
        }
        if (!signupRequest.getBirthDate().matches(BIRTHDATE_FORMAT_REGEX)) {
            signupResponse.addResponse("wrong_date_format");
        }
        if (signup) {
            ld.put(signupRequest.getUsername(), signupRequest.getPassword());
            User user = new User(
                    signupRequest.getUsername(),
                    signupRequest.getPassword(),
                    signupRequest.getFirstName(),
                    signupRequest.getLastName(),
                    signupRequest.getBirthDate(),
                    signupRequest.getGender()
            );
            Database.getInstance().addUser(user.getUsername(), user);
            Database.createUserDirectory(user);
            if (signupRequest.isHasPhoto()) {
                try {
                    ImageMessage image = ((ImageMessage) objectInputStream.readObject());
                    Database.writeImage(user.getUsername(),image.getData(),image.getFormat());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            signupResponse.addResponse("success");
            System.out.println("[ action: register\n" +
                    "\"" + user.getUsername() + "\" registered\n" +
                    "time: " + LocalDateTime.now() + " ]"
            );
        }
        try {
            sendResponse(signupResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendResponse(ServerMessage message) throws IOException {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

}
