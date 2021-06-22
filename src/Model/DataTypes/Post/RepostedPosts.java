package Model.DataTypes.Post;

import Model.DataTypes.User.User;

import java.util.Vector;

public class RepostedPosts implements Posts {
    public static final long serialVersionUID = 500000000L;
    private final Posts post;
    private final String repostUsername;
    private int index;

    public RepostedPosts(Posts post, String repostUsername) {
        this.post = post;
        this.repostUsername = repostUsername;
    }

    @Override
    public User getOwner() {
        return post.getOwner();
    }

    @Override
    public String getDescription() {
        return post.getDescription();
    }

    @Override
    public int getReposts() {
        return post.getReposts();
    }

    @Override
    public int getLikes() {
        return post.getLikes();
    }


    @Override
    public Vector<Comment> getComments() {
        return post.getComments();
    }

    @Override
    public String getTitle() {
        return post.getTitle();
    }

    @Override
    public String getDateAndTime() {
        return post.getDateAndTime();
    }

    @Override
    public long getPublishTime() {
        return post.getPublishTime();
    }

    @Override
    public void like(String username) {
        post.like(username);
    }

    @Override
    public void repost(String username) {
        post.repost(username);
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public String getRepostUsername() {
        return repostUsername;
    }
}
