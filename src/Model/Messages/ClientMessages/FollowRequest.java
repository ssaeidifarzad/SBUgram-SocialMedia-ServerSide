package Model.Messages.ClientMessages;

import Model.DataTypes.User.SafeUser;

public class FollowRequest implements ClientMessage {
    public static final long serialVersionUID = 546512353546L;
    private final SafeUser safeUser;

    public FollowRequest(SafeUser safeUser) {
        this.safeUser = safeUser;
    }

    public SafeUser getSafeUserData() {
        return safeUser;
    }

}
