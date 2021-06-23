package Model.Messages.ClientMessages;

import Model.DataTypes.Post.Posts;

public class UpdatedPostRequest implements ClientMessage {
    public static final long serialVersionUID = 86460002467188498L;
    private final Posts post;

    public UpdatedPostRequest(Posts post) {
        this.post = post;
    }

    public Posts getPost() {
        return post;
    }
}
