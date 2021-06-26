package Model.DataTypes.Post;

import Model.DataTypes.User.SafeUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class Post implements Posts {
    public static final long serialVersionUID = 67746218714634L;
    private SafeUser owner;
    private final String title;
    private final String description;
    private int likes;
    private int reposts;
    private Vector<Comment> comments = new Vector<>();
    private Vector<String> likedUsernames = new Vector<>();
    private Vector<String> repostedUsernames = new Vector<>();
    private Vector<RepostedPosts> repostedPosts = new Vector<>();
    private final String dateAndTime;
    private final long publishTime;
    private int indexInOwnerPosts;
    private final boolean hasPhoto;
    private final byte[] imageData;

    public Post(String title, String description, String dateAndTime, long publishTime, boolean hasPhoto, byte[] imageData) {
        this.title = title;
        this.description = description;
        this.dateAndTime = LocalDateTime.parse(dateAndTime).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.publishTime = publishTime;
        this.hasPhoto = hasPhoto;
        this.imageData = imageData;
    }

    public Post(SafeUser owner, String title, String description, int likes, int reposts, Vector<Comment> comments,
                Vector<String> likedUsernames, Vector<String> repostedUsernames, Vector<RepostedPosts> repostedPosts,
                String dateAndTime, long publishTime, int indexInOwnerPosts, boolean hasPhoto, byte[] imageData) {
        this.title = title;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.publishTime = publishTime;
        this.owner = owner;
        this.likes = likes;
        this.reposts = reposts;
        this.comments = comments;
        this.likedUsernames = likedUsernames;
        this.repostedUsernames = repostedUsernames;
        this.repostedPosts = repostedPosts;
        this.indexInOwnerPosts = indexInOwnerPosts;
        this.hasPhoto = hasPhoto;
        this.imageData = imageData;
    }

    public void setOwner(SafeUser user) {
        this.owner = user;
        update();
    }

    @Override
    public SafeUser getOwner() {
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
        likedUsernames.add(username);
        likes++;
        update();
    }

    @Override
    public void repost(String username, RepostedPosts p) {
        repostedUsernames.add(username);
        reposts++;
        repostedPosts.add(p);
        update();
    }

    public int getIndexInOwnerPosts() {
        return indexInOwnerPosts;
    }

    public void setIndexInOwnerPosts(int indexInOwnerPosts) {
        this.indexInOwnerPosts = indexInOwnerPosts;
    }

    public void leaveComment(Comment comment) {
        comments.add(comment);
    }

    public Vector<String> getLikedUsernames() {
        return likedUsernames;
    }

    public Vector<String> getRepostedUsernames() {
        return repostedUsernames;
    }

    @Override
    public boolean hasPhoto() {
        return hasPhoto;
    }

    @Override
    public byte[] getImageData() {
        return imageData;
    }

    private void update() {
        for (RepostedPosts post : repostedPosts) {
            post.setPost(this);
        }
    }


    public Vector<RepostedPosts> getRepostedPosts() {
        return repostedPosts;
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
