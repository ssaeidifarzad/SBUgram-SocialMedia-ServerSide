package Model.Messages.ClientMessages;

import Model.DataTypes.User.SafeUserData;

public class FollowRequest implements ClientMessage {
    public static final long serialVersionUID = 546512353546L;
    private final SafeUserData safeUserData;

    public FollowRequest(SafeUserData safeUserData) {
        this.safeUserData = safeUserData;
    }

    public SafeUserData getSafeUserData() {
        return safeUserData;
    }

}
