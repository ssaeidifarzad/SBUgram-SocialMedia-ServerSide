package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

import java.util.ArrayList;

public class LoginResponse implements ServerMessage {
    public static final long serialVersionUID = 126485186438474000L;
    private final ArrayList<String> responses = new ArrayList<>();
    private User user;


    public ArrayList<String> getResponses() {
        return responses;
    }

    public void addResponse(String response) {
        responses.add(response);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
