package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

public class FollowResponse implements ServerMessage {
    public static final long serialVersionUID = 888888L;
    private final User user;

    public FollowResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
