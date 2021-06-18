package Model.Messages.ClientMessages;

import Model.DataTypes.User.Gender;

public class SignupRequest implements ClientMessage {
    public static final long serialVersionUID = 222220L;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;

    public SignupRequest(String username, String password, String firstName, String lastName, String birthDate, Gender gender) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
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
}
