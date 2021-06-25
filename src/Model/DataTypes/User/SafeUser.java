package Model.DataTypes.User;

import Model.DataTypes.Post.Posts;

import java.io.Serializable;
import java.util.Vector;

public class SafeUser implements Serializable {
    public static final long serialVersionUID = 16843517865443L;

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final boolean hasPhoto;
    private String photoFormat;
    private final Vector<Posts> posts;
    private final int followersCount;
    private final int followingsCount;

    public SafeUser(String username, String firstName, String lastName, String birthDate, boolean hasPhoto, Vector<Posts> posts, int followersCount, int followingsCount) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hasPhoto = hasPhoto;
        this.posts = posts;
        this.followersCount = followersCount;
        this.followingsCount = followingsCount;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public boolean hasPhoto() {
        return hasPhoto;
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    public Vector<Posts> getPosts() {
        return posts;
    }

    public void setPhotoFormat(String photoFormat) {
        this.photoFormat = photoFormat;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }
}
