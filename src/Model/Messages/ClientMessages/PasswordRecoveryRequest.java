package Model.Messages.ClientMessages;

import java.util.ArrayList;

public class PasswordRecoveryRequest implements ClientMessage {
    public static final long serialVersionUID = 4567891891526005L;
    private final String username;
    private final ArrayList<String> answers;
    private final String newPassword;

    public PasswordRecoveryRequest(String username, ArrayList<String> answers, String newPassword) {
        this.username = username;
        this.answers = answers;
        this.newPassword = newPassword;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getUsername() {
        return username;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
