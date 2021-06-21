package Model.DataTypes.Post;

import Model.DataTypes.User.User;

import java.util.ArrayList;
import java.util.HashSet;

public class Post implements Posts {
    public static final long serialVersionUID = 300000L;

    private final User owner;
    private final String title;
    private final String description;
    private final HashSet<User> likes = new HashSet<>();
    private final HashSet<User> reposts = new HashSet<>();
    private final ArrayList<Comment> comments = new ArrayList<>();
    private final String dateAndTime;
    private final long publishTime;

    public Post(User owner, String title, String description, String dateAndTime, long publishTime) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.publishTime = publishTime;
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
    public HashSet<User> getReposts() {
        return reposts;
    }

    @Override
    public HashSet<User> getLikes() {
        return likes;
    }

    @Override
    public ArrayList<Comment> getComments() {
        return comments;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public long getPublishTime() {
        return publishTime;
    }
}
