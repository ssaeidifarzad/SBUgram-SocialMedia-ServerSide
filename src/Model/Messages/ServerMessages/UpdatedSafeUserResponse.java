package Model.Messages.ServerMessages;

import Model.DataTypes.User.SafeUserData;

public class UpdatedSafeUserResponse implements ServerMessage {
    public static final long serialVersionUID = 537354658417485L;
    private final SafeUserData safeUser;

    public UpdatedSafeUserResponse(SafeUserData safeUser) {
        this.safeUser = safeUser;
    }

    public SafeUserData getSafeUser() {
        return safeUser;
    }
}
