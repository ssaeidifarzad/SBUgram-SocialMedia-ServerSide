package Model.Messages.ClientMessages;

import Model.DataTypes.User.SafeUserData;

public class UnfollowRequest implements ClientMessage {
    public static final long serialVersionUID = 4981361698L;
    private final SafeUserData safeUserData;

    public UnfollowRequest(SafeUserData safeUserData) {
        this.safeUserData = safeUserData;
    }

    public SafeUserData getSafeUserData() {
        return safeUserData;
    }
}
