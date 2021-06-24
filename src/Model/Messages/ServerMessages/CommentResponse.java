package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

public class CommentResponse implements ServerMessage {
    public static final long serialVersionUID = 3473596560345634L;
    private User user;

    public CommentResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
