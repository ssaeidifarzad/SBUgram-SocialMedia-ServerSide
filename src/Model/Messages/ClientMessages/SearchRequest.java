package Model.Messages.ClientMessages;

public class SearchRequest implements ClientMessage {
    public static final long serialVersionUID = 321654456L;
    private final String searchedUsername;

    public SearchRequest(String searchedUsername) {
        this.searchedUsername = searchedUsername;
    }

    public String getSearchedUsername() {
        return searchedUsername;
    }
}
