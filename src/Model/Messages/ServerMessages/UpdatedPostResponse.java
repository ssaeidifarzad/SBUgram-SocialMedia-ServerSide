package Model.Messages.ServerMessages;

import Model.DataTypes.Post.Posts;

public class UpdatedPostResponse implements ServerMessage{
    public static final long serialVersionUID = 86777618946700498L;
    private final Posts post;

    public UpdatedPostResponse(Posts post) {
        this.post = post;
    }

    public Posts getPost() {
        return post;
    }
}
