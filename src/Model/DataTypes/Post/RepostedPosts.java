package Model.DataTypes.Post;

import Model.DataTypes.User.User;

import java.util.ArrayList;
import java.util.HashSet;

public class RepostedPosts implements Posts {
    public static final long serialVersionUID = 500000000L;

    @Override
    public User getOwner() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public HashSet<User> getReposts() {
        return null;
    }

    @Override
    public HashSet<User> getLikes() {
        return null;
    }

    @Override
    public ArrayList<Comment> getComments() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
