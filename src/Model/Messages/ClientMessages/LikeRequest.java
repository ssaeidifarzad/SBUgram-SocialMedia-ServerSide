package Model.Messages.ClientMessages;

import Model.DataTypes.Post.Posts;

public class LikeRequest implements ClientMessage {
    public static final long serialVersionUID = 53468446154641241L;
    private final Posts post;

    public LikeRequest(Posts post) {
        this.post = post;
    }

    public Posts getPost() {
        return post;
    }
}
