package DataBase;

import Model.DataTypes.User.User;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class Database implements Serializable {
    private static Database database;
    private final ConcurrentHashMap<String, String> loginData = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    private Database() {

    }

    public static synchronized void init() {
        try (FileInputStream fileInputStream = new FileInputStream("DataBase.bin");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream("DataBase.bin");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(new Database());
            database = ((Database) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static synchronized void update() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("DataBase.bin");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(database);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (database == null)
            database = new Database();
        return database;
    }

    public ConcurrentHashMap<String, String> getLoginData() {
        return loginData;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addUser(String username, User user) {
        users.put(username, user);
    }
}
