package Model.Messages.ClientMessages;

public class UpdatedSafeUserRequest implements ClientMessage {
    public static final long serialVersionUID = 18658486500546L;
    private final String username;

    public UpdatedSafeUserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
