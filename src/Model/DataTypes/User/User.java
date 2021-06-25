package Model.DataTypes.User;

import Model.DataTypes.Post.Post;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.Post.RepostedPosts;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class User implements Serializable {
    public static final long serialVersionUID = 761141963264241000L;
    private final String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private boolean hasPhoto;
    private String photoFormat;
    private Vector<Posts> posts = new Vector<>();
    private Vector<User> followers = new Vector<>();
    private Vector<User> followings = new Vector<>();
    private int lastPostIndex = 0;
    private Map<SecurityQuestions, String> securityQuestions;

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, String firstName, String lastName,
                String birthDate, boolean hasPhoto, Vector<Posts> posts,
                Vector<User> followers, Vector<User> followings, int lastPostIndex, Map<SecurityQuestions, String> securityQuestions) {
        this(username, password, firstName, lastName, birthDate, hasPhoto, securityQuestions);
        this.posts = posts;
        this.followers = followers;
        this.followings = followings;
        this.lastPostIndex = lastPostIndex;
    }

    public User(String username, String password, String firstName,
                String lastName, String birthDate,
                boolean hasPhoto, Map<SecurityQuestions, String> securityQuestions) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hasPhoto = hasPhoto;
        this.securityQuestions = securityQuestions;
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


    public boolean hasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public void addPost(Posts post) {
        posts.add(post);
        if (post instanceof Post) {
            ((Post) post).setIndexInOwnerPosts(lastPostIndex);
        } else {
            ((RepostedPosts) post).setIndexInRepostUserPosts(lastPostIndex);
        }
        lastPostIndex++;
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

    public Posts getAPost(int index) {
        return posts.get(index);
    }

    public int getLastPostIndex() {
        return lastPostIndex;
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
        return Objects.hash(getUsername(), getPassword(), getFirstName(), getLastName(), getBirthDate(), hasPhoto, getPhotoFormat());
    }

    public Map<SecurityQuestions, String> getSecurityQuestions() {
        return securityQuestions;
    }
}
