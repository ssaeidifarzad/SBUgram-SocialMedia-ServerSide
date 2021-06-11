package DataBase;

import java.util.concurrent.ConcurrentHashMap;

public class Database {
    private static Database database = null;
    ConcurrentHashMap<String, String> loginData;

    private Database() {

    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }
}
