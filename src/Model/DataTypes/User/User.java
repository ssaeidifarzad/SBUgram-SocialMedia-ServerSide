package Model.DataTypes.User;

import Model.DataTypes.Post.Post;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public static final long serialVersionUID = 500000L;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;

    public ArrayList<Post> getPosts() {
        return posts;
    }

    private final ArrayList<Post> posts;

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public ArrayList<User> getFollowings() {
        return followings;
    }

    private final ArrayList<User> followers;
    private final ArrayList<User> followings;

    public User(String username, String password, String firstName, String lastName, String birthDate, Gender gender) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        posts = new ArrayList<>();
        followers = new ArrayList<>();
        followings = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void addPost(Post post) {
        posts.add(post);
    }

    public void addFollower(User user) {
        followers.add(user);
    }

    public void addFollowing(User user) {
        followings.add(user);
    }
}
