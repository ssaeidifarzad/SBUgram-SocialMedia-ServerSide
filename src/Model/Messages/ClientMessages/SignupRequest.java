package Model.Messages.ClientMessages;

import Model.DataTypes.User.Gender;

public class SignupRequest implements ClientMessage {
    public static final long serialVersionUID = 222220L;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;

    public SignupRequest(String username, String password, String firstName, String lastName, int age, Gender gender) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }
}
