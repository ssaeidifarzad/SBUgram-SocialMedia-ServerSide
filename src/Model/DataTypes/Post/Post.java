package Model.DataTypes.Post;

import Model.DataTypes.User.User;

import java.util.Vector;

public class Post implements Posts {
    public static final long serialVersionUID = 300000L;
    private final User owner;
    private final String title;
    private final String description;
    private int likes;
    private int reposts;
    private final Vector<Comment> comments = new Vector<>();
    private final Vector<String> likedUsernames = new Vector<>();
    private final Vector<String> repostedUsernames = new Vector<>();
    private final String dateAndTime;
    private final long publishTime;
    private int index;

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
    public int getReposts() {
        return reposts;
    }

    @Override
    public int getLikes() {
        return likes;
    }


    @Override
    public Vector<Comment> getComments() {
        return comments;
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
        if (!likedUsernames.contains(username)) {
            likedUsernames.add(username);
            likes++;
        }
    }

    @Override
    public void repost(String username) {
        if (!repostedUsernames.contains(username)) {
            repostedUsernames.add(username);
            reposts++;
        }
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }
}
