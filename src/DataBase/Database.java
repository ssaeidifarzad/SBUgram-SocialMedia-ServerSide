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

    public ConcurrentHashMap<String, User> getUsers() {
        return users;
    }

    private Database() {

    }

    public synchronized void init() {
        File data = new File("src/DataBase/DataBaseInitialUserIDS.txt");
        try (FileReader fr = new FileReader(data);
             BufferedReader br = new BufferedReader(fr)) {
            String ID;
            while ((ID = br.readLine()) != null) {
                try {
                    User user = ((User) new ObjectInputStream(new FileInputStream("src/DataBase/UserDirectories/" + ID + "/data.bin")).readObject());
                    loginData.put(user.getUsername(), user.getPassword());
                    users.put(user.getUsername(), user);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (
                IOException e) {
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

    public synchronized void addUser(User user) {
        createUserDirectory(user);
        users.put(user.getUsername(), user);
        loginData.put(user.getUsername(), user.getPassword());
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/DataBase/UserDirectories/"
                + user.getUsername() + "/data.bin"));
             FileWriter fileWriter = new FileWriter("src/DataBase/DataBaseInitialUserIDS.txt", true)) {
            fileWriter.write("\n" + user.getUsername());
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createUserDirectory(User user) {
        try {
            Files.createDirectory(Paths.get("src/DataBase/UserDirectories/" + user.getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
