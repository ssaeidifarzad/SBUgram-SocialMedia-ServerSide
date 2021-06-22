package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

public class UnfollowResponse implements ServerMessage {
    public static final long serialVersionUID = 64651378643578L;
    private final User user;

    public UnfollowResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
