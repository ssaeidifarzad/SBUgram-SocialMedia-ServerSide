package DataBase;

import Model.DataTypes.User.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class UserDataHandler {
    private final User user;
    private final String directoryPath;
    private String photoFormat;

    public UserDataHandler(User user) {
        this.user = user;
        directoryPath = "src/DataBase/UserDirectories/" + user.getUsername();
        photoFormat = user.getPhotoFormat();
    }

    public void updateData() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(directoryPath + "/data.bin"))) {
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeImage(byte[] data, String format) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(data);
        try (FileOutputStream fileOutputStream = new FileOutputStream(directoryPath + "/image." + format)) {
            byteArrayOutputStream.writeTo(fileOutputStream);
            byteArrayOutputStream.close();
            photoFormat = format;
            user.setPhotoFormat(format);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] readImage() {
        byte[] imageData = new byte[0];
        try {
            File photo = new File(directoryPath + "/image." + photoFormat);
            BufferedImage image = ImageIO.read(photo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, photoFormat, byteArrayOutputStream);
            imageData = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    @Override
    public void finalize() {
        updateData();
    }
}
