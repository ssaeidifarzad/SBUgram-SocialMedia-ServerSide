package Model.Messages.ServerMessages;

import java.util.ArrayList;

public class PasswordRecoveryResponse implements ServerMessage {
    public static final long serialVersionUID = 154524000005261L;
    private final ArrayList<String> responses = new ArrayList<>();

    public ArrayList<String> getResponses() {
        return responses;
    }

    public void addResponse(String response) {
        responses.add(response);
    }
}
