package Model.DataTypes.Post;

import Model.DataTypes.User.User;

import java.util.Objects;
import java.util.Vector;

public class Post implements Posts {
    public static final long serialVersionUID = 300000L;
    private User owner;
    private final String title;
    private final String description;
    private int likes;
    private int reposts;
    private final Vector<Comment> comments = new Vector<>();
    private final Vector<String> likedUsernames = new Vector<>();
    private final Vector<String> repostedUsernames = new Vector<>();
    private final Vector<RepostedPosts> repostedPosts = new Vector<>();
    private final String dateAndTime;
    private final long publishTime;

    public Post(String title, String description, String dateAndTime, long publishTime) {
        this.title = title;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.publishTime = publishTime;
    }

    public Post(User owner, String title, String description,
                int likes, int reposts, String dateAndTime, long publishTime) {
        this(title, description, dateAndTime, publishTime);
        this.owner = owner;
        this.likes = likes;
        this.reposts = reposts;
    }

    public void setOwner(User user) {
        this.owner = user;
        update();
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
        update();
    }

    @Override
    public void repost(String username, Posts p) {
        if (!repostedUsernames.contains(username)) {
            repostedUsernames.add(username);
            reposts++;
            repostedPosts.add(new RepostedPosts(p, username));
        }
        update();
    }

    private void update() {
        for (RepostedPosts post : repostedPosts) {
            post.setPost(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return getPublishTime() == post.getPublishTime()
                && owner.getUsername().equals(post.getOwner().getUsername())
                && title.equals(post.getTitle()) && description.equals(post.getDescription())
                && getDateAndTime().equals(post.getDateAndTime());
    }

}
