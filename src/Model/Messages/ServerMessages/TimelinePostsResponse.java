package Model.Messages.ServerMessages;

import Model.DataTypes.Post.Posts;

import java.util.Vector;

public class TimelinePostsResponse implements ServerMessage {
    public static final long serialVersionUID = 416451586431667256L;
    private final Vector<Posts> posts;

    public TimelinePostsResponse(Vector<Posts> posts) {
        this.posts = posts;
    }

    public Vector<Posts> getPosts() {
        return posts;
    }
}
