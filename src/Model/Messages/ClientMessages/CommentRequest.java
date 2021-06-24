package Model.Messages.ClientMessages;

import Model.DataTypes.Post.Comment;
import Model.DataTypes.Post.Posts;

public class CommentRequest implements ClientMessage {
    public static final long serialVersionUID = 23647562095620256L;
    private Posts post;
    private Comment comment;

    public CommentRequest(Posts post, Comment comment) {
        this.post = post;
        this.comment = comment;
    }

    public Posts getPost() {
        return post;
    }

    public Comment getComment() {
        return comment;
    }
}
