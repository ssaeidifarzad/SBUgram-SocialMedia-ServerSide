package Model.DataTypes.User;

import Model.DataTypes.Post.Post;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public static final long serialVersionUID = 500000L;
    private final String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;
    private boolean hasPhoto;
    private String photoFormat;

    private final ArrayList<Post> posts;

    private final ArrayList<User> followers;
    private final ArrayList<User> followings;

    public User(String username, String password, String firstName, String lastName, String birthDate, Gender gender, boolean hasPhoto) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.hasPhoto = hasPhoto;
        posts = new ArrayList<>();
        followers = new ArrayList<>();
        followings = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean hasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void addFollower(User user) {
        followers.add(user);
    }

    public void addFollowing(User user) {
        followings.add(user);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public ArrayList<User> getFollowings() {
        return followings;
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    public void setPhotoFormat(String photoFormat) {
        this.photoFormat = photoFormat;
    }
}
