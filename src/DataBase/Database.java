package DataBase;

import Model.DataTypes.User.User;

import java.util.concurrent.ConcurrentHashMap;

public class Database {
    private static Database database = null;
    private ConcurrentHashMap<String, String> loginData = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    private Database() {

    }

    public static void init() {

    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
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
