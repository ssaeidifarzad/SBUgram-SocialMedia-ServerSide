package Model.Messages.ServerMessages;

import Model.DataTypes.User.User;

import java.util.ArrayList;

public class PublishResponse implements ServerMessage{
    public static final long serialVersionUID = 666661L;
    private ArrayList<String> responses = new ArrayList<>();
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
