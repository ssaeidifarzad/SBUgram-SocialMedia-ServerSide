package Model.Messages.ClientMessages;

public class EditProfileRequest implements ClientMessage {
    public static final long serialVersionUID = 31489671384636454L;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final boolean hasPhoto;

    public EditProfileRequest(String password, String firstName, String lastName, String birthDate, boolean hasPhoto) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hasPhoto = hasPhoto;
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

    public boolean isHasPhoto() {
        return hasPhoto;
    }
}
