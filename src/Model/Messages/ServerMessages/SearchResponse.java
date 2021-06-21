package Model.Messages.ServerMessages;

import Model.DataTypes.User.SafeUserData;

import java.util.ArrayList;

public class SearchResponse implements ServerMessage {
    public static final long serialVersionUID = 75678478961L;
    private ArrayList<String> responses = new ArrayList<>();

    private SafeUserData safeUserData;

    public void addResponse(String response) {
        responses.add(response);
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public SafeUserData getSafeUserData() {
        return safeUserData;
    }

    public void setSafeUserData(SafeUserData safeUserData) {
        this.safeUserData = safeUserData;
    }
}
