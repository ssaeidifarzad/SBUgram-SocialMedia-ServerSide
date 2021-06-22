package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

public class LikeResponse implements ServerMessage {
    public static final long serialVersionUID = 8646416189467188498L;
    private final User user;

    public LikeResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
