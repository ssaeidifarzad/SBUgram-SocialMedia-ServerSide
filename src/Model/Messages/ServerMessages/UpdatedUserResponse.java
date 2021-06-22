package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

public class UpdatedUserResponse implements ServerMessage {
    public static final long serialVersionUID = 23184931879400L;
    private final User user;

    public UpdatedUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
