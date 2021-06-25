package Model.Messages.ClientMessages;

public class gettingOtherUserData implements ClientMessage {
    public static final long serialVersionUID = 574714974192456646L;
    private final String username;

    public gettingOtherUserData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
