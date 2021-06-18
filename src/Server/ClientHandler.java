package Server;

import DataBase.Database;
import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.LoginRequest;
import Model.Messages.ClientMessages.ClientMessage;
import Model.Messages.ClientMessages.SignupRequest;
import Model.Messages.ServerMessages.LoginResponse;
import Model.Messages.ServerMessages.SignupResponse;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;

public class ClientHandler implements Runnable {
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

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
        ClientMessage message;
        while (true) {
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
    }

    private void login(LoginRequest lr) {
        Map<String, String> ld = Database.getInstance().getLoginData();
        LoginResponse loginResponse = new LoginResponse();
        if (ld.containsKey(lr.getUsername())) {
            if (ld.get(lr.getUsername()).equals(lr.getPassword())) {
                loginResponse.addResponse("success");
                System.out.println("[ action: login\n" +
                        "\"" + lr.getUsername() + "\" login\n" +
                        "time: " + LocalDateTime.now() + " ]"
                );
                loginResponse.setUser(Database.getInstance().getUser(lr.getUsername()));
            } else {
                loginResponse.addResponse("wrong_password");
            }
        } else {
            loginResponse.addResponse("no_username");
        }
        try {
            objectOutputStream.writeObject(loginResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signup(SignupRequest signupRequest) {
        SignupResponse signupResponse = new SignupResponse();
        Map<String, String> ld = Database.getInstance().getLoginData();
        if (ld.containsKey(signupRequest.getUsername())) {
            signupResponse.addResponse("unavailable_username");
        }
        if (signupRequest.getPassword().length() < 8) {
            signupResponse.addResponse("wrong_password_format");
        }
        if (signupRequest.getAge() < 0) {
            signupResponse.addResponse("wrong_age");
        }
        if (signupResponse.getResponses().size() == 0) {
            ld.put(signupRequest.getUsername(), signupRequest.getPassword());
            User user = new User(
                    signupRequest.getUsername(),
                    signupRequest.getPassword(),
                    signupRequest.getFirstName(),
                    signupRequest.getLastName(),
                    signupRequest.getAge(),
                    signupRequest.getGender()
            );
            Database.getInstance().addUser(user.getUsername(), user);
            signupResponse.addResponse("success");
        }
        try {
            objectOutputStream.writeObject(signupResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
