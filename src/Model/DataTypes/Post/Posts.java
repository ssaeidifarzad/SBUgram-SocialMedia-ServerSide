package Model.DataTypes.Post;

import Model.DataTypes.User.SafeUser;

import java.io.Serializable;
import java.util.Vector;

public interface Posts extends Serializable {
    long serialVersionUID = 484684841853431L;

    SafeUser getOwner();

    String getDescription();

    int getReposts();

    int getLikes();

    Vector<Comment> getComments();

    String getTitle();

    String getDateAndTime();

    long getPublishTime();

    void like(String username);

    void repost(String username, RepostedPosts p);

    boolean hasPhoto();

    byte[] getImageData();
}
