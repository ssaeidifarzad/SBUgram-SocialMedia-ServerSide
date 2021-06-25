package Model.Messages.ClientMessages;

public class SecurityQuestionsRequest implements ClientMessage{
    public static final long serialVersionUID = 5456488541845241896L;
    private final String username;

    public SecurityQuestionsRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
