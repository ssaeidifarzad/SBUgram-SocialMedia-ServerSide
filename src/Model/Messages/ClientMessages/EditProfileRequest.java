package Model.Messages.ClientMessages;

import Model.DataTypes.User.Gender;

public class EditProfileRequest implements ClientMessage {
    public static final long serialVersionUID = 333330L;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final Gender gender;
    private final boolean hasPhoto;

    public EditProfileRequest(String password, String firstName, String lastName, String birthDate, Gender gender, boolean hasPhoto) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
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

    public Gender getGender() {
        return gender;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }
}
