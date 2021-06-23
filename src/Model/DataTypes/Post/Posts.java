package Model.DataTypes.Post;

import Model.DataTypes.User.User;

import java.io.Serializable;
import java.util.Vector;

public interface Posts extends Serializable {
    long serialVersionUID = 40000000L;

    User getOwner();

    String getDescription();

    int getReposts();

    int getLikes();

    Vector<Comment> getComments();

    String getTitle();

    String getDateAndTime();

    long getPublishTime();

    void like(String username);

    void repost(String username, Posts p);
}
