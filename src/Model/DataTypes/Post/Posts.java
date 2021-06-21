package Model.DataTypes.Post;

import Model.DataTypes.User.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public interface Posts extends Serializable {
    long serialVersionUID = 40000000L;

    User getOwner();

    String getDescription();

    HashSet<User> getReposts();

    HashSet<User> getLikes();

    ArrayList<Comment> getComments();

    String getTitle();
}
