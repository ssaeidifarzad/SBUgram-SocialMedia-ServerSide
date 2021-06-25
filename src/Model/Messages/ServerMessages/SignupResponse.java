package Model.Messages.ServerMessages;

import java.util.ArrayList;

public class SignupResponse implements ServerMessage {
    public static final long serialVersionUID = 4174698347183614634L;
    private final ArrayList<String> responses = new ArrayList<>();

    public void addResponse(String response) {
        responses.add(response);
    }

    public ArrayList<String> getResponses() {
        return responses;
    }
}
