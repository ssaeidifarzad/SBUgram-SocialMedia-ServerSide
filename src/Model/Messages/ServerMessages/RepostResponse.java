package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

import java.util.ArrayList;

public class RepostResponse implements ServerMessage {
    public static final long serialVersionUID = 864641651789188498L;
    private final ArrayList<String> responses = new ArrayList<>();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public void addResponse(String response) {
        responses.add(response);
    }
}
