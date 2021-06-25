package Model.Messages.ServerMessages;

import Model.DataTypes.User.SafeUser;

public class UpdatedSafeUserResponse implements ServerMessage {
    public static final long serialVersionUID = 537354658417485L;
    private final SafeUser safeUser;

    public UpdatedSafeUserResponse(SafeUser safeUser) {
        this.safeUser = safeUser;
    }

    public SafeUser getSafeUser() {
        return safeUser;
    }
}
