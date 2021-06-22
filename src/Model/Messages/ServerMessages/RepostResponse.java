package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

public class RepostResponse implements ServerMessage {
    public static final long serialVersionUID = 864641651789188498L;
    private final User user;

    public RepostResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
