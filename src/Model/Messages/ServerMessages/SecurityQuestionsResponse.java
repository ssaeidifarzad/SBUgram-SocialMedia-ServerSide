package Model.Messages.ServerMessages;

import java.util.ArrayList;

public class SecurityQuestionsResponse implements ServerMessage {
    public static final long serialVersionUID = 1646144564466120236L;
    private final ArrayList<String> responses = new ArrayList<>();
    private ArrayList<String> questions;

    public ArrayList<String> getResponses() {
        return responses;
    }

    public void addResponse(String response) {
        responses.add(response);
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }
}
