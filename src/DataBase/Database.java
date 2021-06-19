package DataBase;

import Model.DataTypes.User.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

public class Database implements Serializable {
    public static final long serialVersionUID = 23453156454L;
    private static Database database;
    private final ConcurrentHashMap<String, String> loginData = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    private Database() {

    }

    private void createUserDirectory(User user) {
        try {
            Files.createDirectory(Paths.get("src/DataBase/UserDirectories/" + user.getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeImage(String username, byte[] data, String format) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(data);
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/DataBase/UserDirectories/" + username + "/image." + format)) {
            byteArrayOutputStream.writeTo(fileOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void init() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/DataBase/DataBaseInitial.bin"))) {
            database = ((Database) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static synchronized void update() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/DataBase/DataBaseInitial.bin"))) {
            objectOutputStream.writeObject(database);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        return database;
    }

    public ConcurrentHashMap<String, String> getLoginData() {
        return loginData;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addUser(User user) {
        createUserDirectory(user);
        users.put(user.getUsername(), user);
        loginData.put(user.getUsername(), user.getPassword());
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/DataBase/UserDirectories/"
                + user.getUsername() + "/data.bin"))) {
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
