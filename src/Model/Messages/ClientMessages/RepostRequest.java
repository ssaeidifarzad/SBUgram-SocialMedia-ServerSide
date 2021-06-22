package Model.Messages.ClientMessages;

import Model.DataTypes.Post.Posts;

public class RepostRequest implements ClientMessage {
    public static final long serialVersionUID = 534684461546416741L;
    private final Posts post;

    public RepostRequest(Posts post) {
        this.post = post;
    }

    public Posts getPost() {
        return post;
    }
}
