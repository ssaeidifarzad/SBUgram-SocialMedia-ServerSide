package Model.Messages.ClientMessages;

import Model.DataTypes.User.SafeUser;

public class UnfollowRequest implements ClientMessage {
    public static final long serialVersionUID = 4981361698L;
    private final SafeUser safeUser;

    public UnfollowRequest(SafeUser safeUser) {
        this.safeUser = safeUser;
    }

    public SafeUser getSafeUserData() {
        return safeUser;
    }
}
