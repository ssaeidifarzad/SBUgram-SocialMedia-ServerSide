package Model.Messages.ClientMessages;

public class LoginRequest implements ClientMessage {
    public static final long serialVersionUID = 4638717613481048673L;
    private final String username;
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
