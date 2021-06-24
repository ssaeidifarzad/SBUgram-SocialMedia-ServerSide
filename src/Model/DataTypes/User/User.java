package Model.DataTypes.User;
import Model.DataTypes.Post.Posts;

import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

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
    private Vector<Posts> posts = new Vector<>();
    private Vector<User> followers = new Vector<>();
    private Vector<User> followings = new Vector<>();

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, String firstName, String lastName, String birthDate, Gender gender, boolean hasPhoto, Vector<Posts> posts,
                Vector<User> followers, Vector<User> followings) {
        this(username, password, firstName, lastName, birthDate, gender, hasPhoto);
        this.posts = posts;
        this.followers = followers;
        this.followings = followings;
    }

    public User(String username, String password, String firstName, String lastName, String birthDate, Gender gender, boolean hasPhoto) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.hasPhoto = hasPhoto;
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

    public void addPost(Posts post) {
        posts.add(post);
    }

    public void addFollower(User user) {
        followers.add(user);
    }

    public void addFollowing(User user) {
        followings.add(user);
    }

    public Vector<Posts> getPosts() {
        return posts;
    }

    public Vector<User> getFollowers() {
        return followers;
    }

    public Vector<User> getFollowings() {
        return followings;
    }

    public boolean containsFollower(String username) {
        for (User u : followers) {
            if (u.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public boolean containsFollowing(String username) {
        for (User u : followings) {
            if (u.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public Posts getAPost(Posts post) {
        for (Posts p : posts) {
            if (p.equals(post)) {
                return p;
            }
        }
        return null;
    }

    public void unfollow(String username) {
        followings.remove(new User(username));
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    public void setPhotoFormat(String photoFormat) {
        this.photoFormat = photoFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getFirstName(), getLastName(), getBirthDate(), getGender(), hasPhoto, getPhotoFormat());
    }
}
