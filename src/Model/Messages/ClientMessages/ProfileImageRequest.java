package Model.Messages.ClientMessages;

public class ProfileImageRequest implements ClientMessage {
    public static final long serialVersionUID = 654789198418600015L;
    private final String username;

    public ProfileImageRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
