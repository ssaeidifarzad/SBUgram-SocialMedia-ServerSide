package Model.Messages.ClientMessages;
import Model.DataTypes.User.SecurityQuestions;

import java.util.Map;

public class SignupRequest implements ClientMessage {
    public static final long serialVersionUID = 222220L;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private boolean hasPhoto;
    private final Map<SecurityQuestions, String> securityQuestions;

    public SignupRequest(String username, String password, String firstName, String lastName,
                         String birthDate, boolean hasPhoto, Map<SecurityQuestions, String> securityQuestions) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hasPhoto = hasPhoto;
        this.securityQuestions = securityQuestions;
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

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public Map<SecurityQuestions, String> getSecurityQuestions() {
        return securityQuestions;
    }
}
