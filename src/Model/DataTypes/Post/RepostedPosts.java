package Model.DataTypes.Post;

import Model.DataTypes.User.User;

import java.util.Date;
import java.util.Vector;

public class RepostedPosts implements Posts {
    public static final long serialVersionUID = 500000000L;
    private final User owner;
    private final String title;
    private final String description;
    private final String dateAndTime;
    private final long publishTime;
    private Posts post;
    private final String repostUsername;
    private final long repostTime;

    public RepostedPosts(Posts post, String repostUsername) {
        owner = post.getOwner();
        this.post = post;
        title = post.getTitle();
        description = post.getDescription();
        dateAndTime = post.getDateAndTime();
        publishTime = post.getPublishTime();
        this.repostUsername = repostUsername;
        repostTime = new Date().getTime();
    }

    public RepostedPosts(User owner, String title, String description, String dateAndTime,
                         long publishTime, Posts post, String repostUsername, long repostTime) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.publishTime = publishTime;
        this.post = post;
        this.repostUsername = repostUsername;
        this.repostTime = repostTime;
    }

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public String getDescription() {
        return description;
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
        return title;
    }

    @Override
    public String getDateAndTime() {
        return dateAndTime;
    }

    @Override
    public long getPublishTime() {
        return publishTime;
    }

    @Override
    public void like(String username) {
        post.like(username);
    }

    @Override
    public void repost(String username, RepostedPosts p) {
        post.repost(username, p);
    }

    public String getRepostUsername() {
        return repostUsername;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public Posts getPost() {
        return post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RepostedPosts))
            return false;
        RepostedPosts posts = (RepostedPosts) o;
        return getOwner().getUsername().equals(posts.getOwner().getUsername())
                && getRepostTime() == posts.getRepostTime()
                && getRepostUsername().equals(posts.getRepostUsername());
    }

    public long getRepostTime() {
        return repostTime;
    }
}
