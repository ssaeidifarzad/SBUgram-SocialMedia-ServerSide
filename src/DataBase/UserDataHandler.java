package DataBase;

import Model.DataTypes.User.User;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserDataHandler {
    private final User user;
    private final String directoryPath;

    public UserDataHandler(User user) {
        this.user = user;
        directoryPath = "src/DataBase/UserDirectories/" + user.getUsername();
    }

    public void updateData() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(directoryPath + "/data.bin"))) {
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeImage(String username, byte[] data, String format) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(data);
        try (FileOutputStream fileOutputStream = new FileOutputStream(directoryPath + "/image." + format)) {
            byteArrayOutputStream.writeTo(fileOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalize(){
        updateData();
    }
}
