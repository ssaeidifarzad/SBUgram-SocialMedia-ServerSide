package Server;

import DataBase.Database;
import DataBase.UserDataHandler;
import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.*;
import Model.Messages.ServerMessages.EditProfileResponse;
import Model.Messages.ServerMessages.LoginResponse;
import Model.Messages.ServerMessages.ServerMessage;
import Model.Messages.ServerMessages.SignupResponse;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private User user;
    private UserDataHandler userDataHandler;
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
                } else if ((message instanceof EditProfileRequst)) {
                    editProfile(((EditProfileRequst) message));
                } else if (message instanceof LogoutRequest) {
                    logout();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (userDataHandler != null) {
                userDataHandler.updateData();
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
                userDataHandler = new UserDataHandler(user);
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
        sendResponse(loginResponse);
    }

    private void signup(SignupRequest signupRequest) {
        SignupResponse signupResponse = new SignupResponse();
        boolean signup = true;
        if (Database.getInstance().getLoginData().containsKey(signupRequest.getUsername())) {
            signupResponse.addResponse("unavailable_username");
            signup = false;
        }
        if (signupRequest.getPassword().length() < 8) {
            signupResponse.addResponse("wrong_password_format");
            signup = false;
        }
        if (!signupRequest.getBirthDate().matches(BIRTHDATE_FORMAT_REGEX)) {
            signupResponse.addResponse("wrong_date_format");
            signup = false;
        }
        if (signup) {
            User user = new User(
                    signupRequest.getUsername(),
                    signupRequest.getPassword(),
                    signupRequest.getFirstName(),
                    signupRequest.getLastName(),
                    signupRequest.getBirthDate(),
                    signupRequest.getGender()
            );
            Database.getInstance().addUser(user);
            UserDataHandler udh = new UserDataHandler(user);
            if (signupRequest.isHasPhoto()) {
                try {
                    ImageMessage image = ((ImageMessage) objectInputStream.readObject());
                    udh.writeImage(user.getUsername(), image.getData(), image.getFormat());
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
        sendResponse(signupResponse);
    }

    private void editProfile(EditProfileRequst epr) {
        EditProfileResponse response = new EditProfileResponse();
        boolean edit = true;
        if (epr.getPassword().length() < 8) {
            response.addResponse("wrong_password_format");
            edit = false;
        }
        if (!epr.getBirthDate().matches(BIRTHDATE_FORMAT_REGEX)) {
            response.addResponse("wrong_date_format");
            edit = false;
        }
        if (edit) {
            response.addResponse("success");
            user.setFirstName(epr.getFirstName());
            user.setLastName(epr.getLastName());
            user.setBirthDate(epr.getBirthDate());
            user.setPassword(epr.getPassword());
            user.setGender(epr.getGender());
            response.setUser(new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getBirthDate(),
                    user.getGender()
            ));
            Database.getInstance().getLoginData().put(user.getUsername(), user.getPassword());
            System.out.println("[ action: update info\n" +
                    "\"" + user.getUsername() + "\" updated their info\n" +
                    "time: " + LocalDateTime.now() + " ]"
            );
        }
        sendResponse(response);
    }

    private void logout() {
        System.out.println("[ action: logout\n" +
                "\"" + user.getUsername() + "\" logged out\n" +
                "time: " + LocalDateTime.now() + " ]"
        );
        user = null;
        userDataHandler = null;
    }

    private void sendResponse(ServerMessage message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printServerMessage() {

    }
}
