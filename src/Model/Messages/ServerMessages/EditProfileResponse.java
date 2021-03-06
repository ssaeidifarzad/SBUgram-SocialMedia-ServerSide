package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

import java.util.ArrayList;

public class EditProfileResponse implements ServerMessage {
    public static final long serialVersionUID = 461476387861634L;
    private final ArrayList<String> responses = new ArrayList<>();
    private User user;

    public ArrayList<String> getResponses() {
        return responses;
    }

    public void addResponse(String response) {
        responses.add(response);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
