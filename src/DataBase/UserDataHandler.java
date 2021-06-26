package DataBase;

import Model.DataTypes.User.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

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

    public void writeProfileImage(byte[] data) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(data);
        try (FileOutputStream fileOutputStream = new FileOutputStream(directoryPath + "/image.jpg")) {
            byteArrayOutputStream.writeTo(fileOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] writeImageToArray() {
        byte[] imageData = new byte[0];
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            File photo = new File(directoryPath + "/image.jpg");
            BufferedImage image = ImageIO.read(photo);
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            imageData = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }

}
