package Model.Messages.ServerMessages;

import Model.DataTypes.User.SafeUser;

import java.util.ArrayList;

public class SearchResponse implements ServerMessage {
    public static final long serialVersionUID = 75678478961L;
    private final ArrayList<String> responses = new ArrayList<>();

    private SafeUser safeUser;

    public void addResponse(String response) {
        responses.add(response);
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public SafeUser getSafeUserData() {
        return safeUser;
    }

    public void setSafeUserData(SafeUser safeUser) {
        this.safeUser = safeUser;
    }
}
