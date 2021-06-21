package Model.Messages.ClientMessages;

import Model.DataTypes.Post.Posts;

public class PublishRequest implements ClientMessage {
    public static final long serialVersionUID = 666660L;
    Posts post;

    public PublishRequest(Posts post) {
        this.post = post;
    }

    public Posts getPost() {
        return post;
    }

}
