package Model.Messages.ServerMessages;

import java.util.ArrayList;

public class SignupResponse implements ServerMessage {
    public static final long serialVersionUID = 222221L;
    private ArrayList<String> responses = new ArrayList<>();

    public void addResponse(String response) {

    }

    public ArrayList<String> getResponses() {
        return responses;
    }
}
