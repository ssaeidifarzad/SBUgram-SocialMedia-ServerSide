package Model.Messages.ClientMessages;

public class EditProfileRequest implements ClientMessage {
    public static final long serialVersionUID = 31489671384636454L;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final boolean hasPhoto;
    private final byte[] imageData;

    public EditProfileRequest(String password, String firstName, String lastName, String birthDate, boolean hasPhoto, byte[] imageData) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hasPhoto = hasPhoto;
        this.imageData = imageData;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public boolean hasPhoto() {
        return hasPhoto;
    }

    public byte[] getImageData() {
        return imageData;
    }
}
