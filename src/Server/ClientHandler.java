package Server;

import DataBase.Database;
import DataBase.UserDataHandler;
import Model.DataTypes.Post.Post;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.Post.RepostedPosts;
import Model.DataTypes.User.SafeUser;
import Model.DataTypes.User.SecurityQuestions;
import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.*;
import Model.Messages.ImageMessage;
import Model.Messages.ServerMessages.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private User user;
    private UserDataHandler userDataHandler;

    //make it method
    public static final String BIRTHDATE_FORMAT_REGEX = "(19|20)[0-9]{2}/(1[0-2]|[1-9])/([1-9]|[1-2][0-9]|3[0-1])";

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ClientMessage message = null;
        while (!(message instanceof ExitMessage)) {
            try {
                message = ((ClientMessage) objectInputStream.readObject());
                if (message instanceof LoginRequest) {
                    login((LoginRequest) message);
                } else if (message instanceof SignupRequest) {
                    signup(((SignupRequest) message));
                } else if ((message instanceof EditProfileRequest)) {
                    editProfile(((EditProfileRequest) message));
                } else if (message instanceof LogoutRequest) {
                    logout();
                } else if (message instanceof OwnerImageRequest) {
                    sendImage();
                } else if (message instanceof PublishRequest) {
                    publish(((PublishRequest) message));
                } else if (message instanceof SearchRequest) {
                    search(((SearchRequest) message));
                } else if (message instanceof FollowRequest) {
                    follow(((FollowRequest) message));
                } else if (message instanceof UnfollowRequest) {
                    unfollow(((UnfollowRequest) message));
                } else if (message instanceof TimelinePostsRequest) {
                    sendPosts();
                } else if (message instanceof RepostRequest) {
                    repost(((RepostRequest) message));
                } else if (message instanceof LikeRequest) {
                    like(((LikeRequest) message));
                } else if (message instanceof UpdatedUserRequest) {
                    sendResponse(new UpdatedUserResponse(createNewUser()));
                } else if (message instanceof UpdatedSafeUserRequest) {
                    sendResponse(new UpdatedSafeUserResponse(createSafeUser(
                            Database.getInstance().getUser(((UpdatedSafeUserRequest) message).getUsername())
                    )));
                } else if (message instanceof UpdatedPostRequest) {
                    updatePost(((UpdatedPostRequest) message));
                } else if (message instanceof ProfileImageRequest) {
                    sendOtherProfileImage(Database.getInstance().getUser(((ProfileImageRequest) message).getUsername()));
                } else if (message instanceof CommentRequest) {
                    leaveComment(((CommentRequest) message));
                } else if (message instanceof gettingOwnerData) {
                    String address = "";
                    if (user.hasPhoto()) {
                        address = "DataBase/UserDirectories/"
                                + user.getUsername() + "/image.jpg";
                    }
                    printServerMessage("getting info", "got their info - " + address);
                } else if (message instanceof gettingOtherUserData) {
                    String address = "";
                    User u = Database.getInstance().getUser(((gettingOtherUserData) message).getUsername());
                    if (u.hasPhoto()) {
                        address = "DataBase/UserDirectories/" +
                                u.getUsername() + "/image.jpg";
                    }
                    printServerMessage("getting info", "got "
                            + ((gettingOtherUserData) message).getUsername() + "'s info - " + address);
                } else if (message instanceof PasswordRecoveryRequest) {
                    recoverPassword(((PasswordRecoveryRequest) message));
                } else if (message instanceof SecurityQuestionsRequest) {
                    sendSecurityQuestions(((SecurityQuestionsRequest) message));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (user != null) {
                Database.getInstance().update();
            }
        }
        if (user != null) {
            printServerMessage("disconnect", "disconnected");
        }
        disconnect();
    }

    private void disconnect() {
        try {
            socket.close();
            objectInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(LoginRequest lr) {
        Map<String, String> ld = Database.getInstance().getLoginData();
        LoginResponse loginResponse = new LoginResponse();
        if (ld.containsKey(lr.getUsername())) {
            if (ld.get(lr.getUsername()).equals(lr.getPassword())) {
                loginResponse.addResponse("success");
                user = Database.getInstance().getUser(lr.getUsername());
                userDataHandler = new UserDataHandler(user);
                loginResponse.setUser(createNewUser());
                printServerMessage("connect,login", "signed in");
            } else {
                loginResponse.addResponse("wrong_password");
            }
        } else {
            loginResponse.addResponse("no_username");
        }
        sendResponse(loginResponse);
    }

    private void signup(SignupRequest signupRequest) {
        SignupResponse signupResponse = new SignupResponse();
        boolean signup = true;
        if (Database.getInstance().getLoginData().containsKey(signupRequest.getUsername())) {
            signupResponse.addResponse("unavailable_username");
            signup = false;
        }
        if (signupRequest.getPassword().length() < 8) {
            signupResponse.addResponse("wrong_password_format");
            signup = false;
        }
        if (!signupRequest.getBirthDate().matches(BIRTHDATE_FORMAT_REGEX)) {
            signupResponse.addResponse("wrong_date_format");
            signup = false;
        }
        if (signup) {
            User user = new User(
                    signupRequest.getUsername(),
                    signupRequest.getPassword(),
                    signupRequest.getFirstName(),
                    signupRequest.getLastName(),
                    signupRequest.getBirthDate(),
                    signupRequest.hasPhoto(),
                    signupRequest.getSecurityQuestions()
            );
            Database.getInstance().addUser(user);
            UserDataHandler udh = new UserDataHandler(user);
            String address = "";
            if (signupRequest.hasPhoto()) {
                udh.writeProfileImage(signupRequest.getImageData());
                address = "DataBase/UserDirectories/" +
                        signupRequest.getUsername() + "/image.jpg";
            }
            signupResponse.addResponse("success");
            System.out.println("[ action: register\n" +
                    "\"" + user.getUsername() + "\" registered - " + address + "\n" +
                    "time: " + LocalDateTime.now() + " ]\n");
        }
        sendResponse(signupResponse);
    }

    private void editProfile(EditProfileRequest editProfileRequest) {
        EditProfileResponse response = new EditProfileResponse();
        boolean edit = true;
        if (editProfileRequest.getPassword().length() < 8) {
            response.addResponse("wrong_password_format");
            edit = false;
        }
        if (!editProfileRequest.getBirthDate().matches(BIRTHDATE_FORMAT_REGEX)) {
            response.addResponse("wrong_date_format");
            edit = false;
        }
        if (edit) {
            response.addResponse("success");
            user.setFirstName(editProfileRequest.getFirstName());
            user.setLastName(editProfileRequest.getLastName());
            user.setBirthDate(editProfileRequest.getBirthDate());
            user.setPassword(editProfileRequest.getPassword());
            user.setHasPhoto(editProfileRequest.hasPhoto());
            Database.getInstance().getLoginData().put(user.getUsername(), user.getPassword());
            Database.getInstance().getUsers().put(user.getUsername(), user);
            response.setUser(createNewUser());
            sendResponse(response);
            String address = "";
            if (editProfileRequest.hasPhoto()) {
                    userDataHandler.writeProfileImage(editProfileRequest.getImageData());
                    address = "DataBase/UserDirectories/" + user.getUsername() + "/image.jpg";
            }
            printServerMessage("update info - " + address, "updated their info");
        } else {
            sendResponse(response);
        }
    }

    private void publish(PublishRequest publishRequest) {
        PublishResponse publishResponse = new PublishResponse();
        Posts p = publishRequest.getPost();
        ((Post) p).setOwner(createSafeUser(user));
        user.addPost(p);
        publishResponse.addResponse("success");
        publishResponse.setUser(createNewUser());
        printServerMessage("publish", "published a post");
        sendResponse(publishResponse);
    }

    private void logout() {
        printServerMessage("logout", "signed out");
        user = null;
        userDataHandler = null;
    }

    private void search(SearchRequest searchRequest) {
        SearchResponse searchResponse = new SearchResponse();
        if (Database.getInstance().getUsers().containsKey(searchRequest.getSearchedUsername())) {
            searchResponse.addResponse("success");
            searchResponse.setSafeUserData(createSafeUser(Database.getInstance()
                    .getUser(searchRequest.getSearchedUsername())));
            sendResponse(searchResponse);
            if (searchResponse.getSafeUserData().hasPhoto()) {
                sendOtherProfileImage(Database.getInstance().getUser(searchRequest.getSearchedUsername()));
            }
        } else {
            searchResponse.addResponse("no_user");
            sendResponse(searchResponse);
        }
    }

    private void follow(FollowRequest followRequest) {
        user.addFollowing(Database.getInstance().getUser(followRequest.getSafeUserData().getUsername()));
        Database.getInstance().getUser(followRequest.getSafeUserData().getUsername()).addFollower(user);
        sendResponse(new FollowResponse(createNewUser()));
        printServerMessage("follow", "started following " + "\""
                + followRequest.getSafeUserData().getUsername() + "\"");
    }

    private void unfollow(UnfollowRequest unfollowRequest) {
        user.unfollow(unfollowRequest.getSafeUserData().getUsername());
        Database.getInstance().getUser(unfollowRequest.getSafeUserData().getUsername())
                .getFollowers().remove(user);
        sendResponse(new UnfollowResponse(createNewUser()));
        printServerMessage("unfollow", "unfollowed " +
                "\"" + unfollowRequest.getSafeUserData().getUsername() + "\"");
    }

    private void sendPosts() {
        Vector<Posts> posts = new Vector<>();
        for (User u : user.getFollowings()) {
            posts.addAll(createNewPosts(u.getPosts()));
        }
        posts.addAll(user.getPosts());
        sendResponse(new TimelinePostsResponse(posts));
        printServerMessage("get posts list", "got their timeline posts");
    }

    private void repost(RepostRequest repostRequest) {
        RepostResponse repostResponse = new RepostResponse();
        User receivedUser = Database.getInstance().getUser(repostRequest.getPost().getOwner().getUsername());
        Posts receivedPost;
        if (repostRequest.getPost() instanceof RepostedPosts) {
            receivedPost = receivedUser.getAPost(((Post) ((RepostedPosts) repostRequest.getPost()).getPost()).getIndexInOwnerPosts());
        } else {
            receivedPost = receivedUser.getAPost(((Post) repostRequest.getPost()).getIndexInOwnerPosts());
        }
        if (((Post) receivedPost).getRepostedUsernames().contains(user.getUsername())) {
            repostResponse.addResponse("fail");
            repostResponse.setUser(createNewUser());
            sendResponse(repostResponse);
            return;
        }
        RepostedPosts rp = new RepostedPosts(receivedPost, user.getUsername());
        user.addPost(rp);
        receivedPost.repost(user.getUsername(), rp);
        repostResponse.setUser(createNewUser());
        repostResponse.addResponse("success");
        sendResponse(repostResponse);
        printServerMessage("repost", "reposted " + rp.getPost().getOwner().getUsername()
                + "'s post: " + rp.getTitle());
    }

    private void like(LikeRequest likeRequest) {
        LikeResponse likeResponse = new LikeResponse();
        User receivedUser = Database.getInstance().getUser(likeRequest.getPost().getOwner().getUsername());
        Posts receivedPost;
        if (likeRequest.getPost() instanceof RepostedPosts) {
            receivedPost = receivedUser.getAPost(((Post) ((RepostedPosts) likeRequest.getPost()).getPost()).getIndexInOwnerPosts());
        } else {
            receivedPost = receivedUser.getAPost(((Post) likeRequest.getPost()).getIndexInOwnerPosts());
        }
        if (((Post) receivedPost).getLikedUsernames().contains(user.getUsername())) {
            likeResponse.addResponse("fail");
            likeResponse.setUser(createNewUser());
            sendResponse(likeResponse);
            return;
        }
        receivedUser.getAPost(((Post) receivedPost).getIndexInOwnerPosts()).like(user.getUsername());
        likeResponse.addResponse("success");
        likeResponse.setUser(createNewUser());
        sendResponse(likeResponse);
        printServerMessage("like", "liked " + receivedPost.getOwner()
                .getUsername() + "'s post : " + receivedPost.getTitle());
    }

    private void leaveComment(CommentRequest commentRequest) {
        Posts p = commentRequest.getPost();
        User u = Database.getInstance().getUser(p.getOwner().getUsername());
        Posts post;
        if (p instanceof RepostedPosts) {
            post = u.getAPost(((Post) ((RepostedPosts) p).getPost()).getIndexInOwnerPosts());
        } else {
            post = u.getAPost(((Post) p).getIndexInOwnerPosts());
        }
        ((Post) post).leaveComment(commentRequest.getComment());
        sendResponse(new CommentResponse(createNewUser()));
        printServerMessage("comment", "left a comment on " +
                post.getOwner().getUsername() + "'s post: ");
    }

    private void recoverPassword(PasswordRecoveryRequest passwordRecoveryRequest) {
        PasswordRecoveryResponse passwordRecoveryResponse = new PasswordRecoveryResponse();
        User user = Database.getInstance().getUser(passwordRecoveryRequest.getUsername());
        boolean confirm = true;
        int i = 0;
        if (passwordRecoveryRequest.getNewPassword().length() < 8) {
            passwordRecoveryResponse.addResponse("wrong_password_format");
            confirm = false;
        }
        for (Map.Entry<SecurityQuestions, String> entry : user.getSecurityQuestions().entrySet()) {
            if (!entry.getValue().equals(passwordRecoveryRequest.getAnswers().get(i++))) {
                passwordRecoveryResponse.addResponse("wrong_answers");
                confirm = false;
                break;
            }
        }
        if (confirm) {
            passwordRecoveryResponse.addResponse("success");
            user.setPassword(passwordRecoveryRequest.getNewPassword());
            Database.getInstance().getLoginData().put(user.getUsername(), passwordRecoveryRequest.getNewPassword());
            System.out.println("[ action: password recovery\n" +
                    "\"" + user.getUsername() + "\" changed their password\n" +
                    "time: " + LocalDateTime.now() + " ]\n");
        }
        sendResponse(passwordRecoveryResponse);
    }

    private void sendSecurityQuestions(SecurityQuestionsRequest securityQuestionsRequest) {
        SecurityQuestionsResponse securityQuestionsResponse = new SecurityQuestionsResponse();
        User user = Database.getInstance().getUser(securityQuestionsRequest.getUsername());
        if (user == null) {
            securityQuestionsResponse.addResponse("no_user");
            sendResponse(securityQuestionsResponse);
            return;
        }
        ArrayList<String> questions = new ArrayList<>();
        for (SecurityQuestions s : user.getSecurityQuestions().keySet()) {
            questions.add(s.getQuestion());
        }
        securityQuestionsResponse.addResponse("success");
        securityQuestionsResponse.setQuestions(questions);
        sendResponse(securityQuestionsResponse);
    }

    private void sendImage() {
        try {
            objectOutputStream.writeObject(new ImageMessage(userDataHandler.writeImageToArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendOtherProfileImage(User user) {
        UserDataHandler userDataHandler = new UserDataHandler(user);
        try {
            objectOutputStream.writeObject(new ImageMessage(userDataHandler.writeImageToArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(ServerMessage message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User createNewUser() {
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.hasPhoto(),
                createNewPosts(user.getPosts()),
                new Vector<>(user.getFollowers()),
                new Vector<>(user.getFollowings()),
                user.getLastPostIndex(),
                user.getSecurityQuestions()
        );
    }

    private SafeUser createSafeUser(User user) {
        return new  SafeUser(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.hasPhoto(),
                createNewPosts(user.getPosts()),
                user.getFollowers().size(),
                user.getFollowings().size());
    }

    private void updatePost(UpdatedPostRequest updatedPostRequest) {
        Posts p = updatedPostRequest.getPost();
        User u;
        Posts receivedPost;
        if (p instanceof RepostedPosts) {
            u = Database.getInstance().getUser(((RepostedPosts) p).getRepostUsername());
            receivedPost = u.getAPost(((RepostedPosts) p).getIndexInRepostUserPosts());
        } else {
            u = Database.getInstance().getUser(p.getOwner().getUsername());
            receivedPost = u.getAPost(((Post) p).getIndexInOwnerPosts());
        }
        sendResponse(new UpdatedPostResponse(createNewPost(receivedPost)));
    }

    private Vector<Posts> createNewPosts(Vector<Posts> posts) {
        Vector<Posts> temp = new Vector<>();
        for (Posts p : posts) {
            if (p instanceof RepostedPosts) {
                temp.add(new RepostedPosts(
                        p.getOwner(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getDateAndTime(),
                        ((RepostedPosts) p).getPost(),
                        ((RepostedPosts) p).getRepostUsername(),
                        ((RepostedPosts) p).getRepostTime(),
                        ((RepostedPosts) p).getIndexInRepostUserPosts()));
            } else {
                temp.add(new Post(
                        p.getOwner(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getLikes(),
                        p.getReposts(),
                        p.getComments(),
                        ((Post) p).getLikedUsernames(),
                        ((Post) p).getRepostedUsernames(),
                        ((Post) p).getRepostedPosts(),
                        p.getDateAndTime(),
                        p.getPublishTime(),
                        ((Post) p).getIndexInOwnerPosts()
                ));
            }
        }
        return temp;
    }

    private Posts createNewPost(Posts p) {
        Posts post;
        if (p instanceof RepostedPosts) {
            post = new RepostedPosts(
                    p.getOwner(),
                    p.getTitle(),
                    p.getDescription(),
                    p.getDateAndTime(),
                    createNewPost(((RepostedPosts) p).getPost()),
                    ((RepostedPosts) p).getRepostUsername(),
                    ((RepostedPosts) p).getRepostTime(),
                    ((RepostedPosts) p).getIndexInRepostUserPosts());
        } else {
            post = new Post(
                    p.getOwner(),
                    p.getTitle(),
                    p.getDescription(),
                    p.getLikes(),
                    p.getReposts(),
                    new Vector<>(p.getComments()),
                    new Vector<>(((Post) p).getLikedUsernames()),
                    new Vector<>(((Post) p).getRepostedUsernames()),
                    ((Post) p).getRepostedPosts(),
                    p.getDateAndTime(),
                    p.getPublishTime(),
                    ((Post) p).getIndexInOwnerPosts()
            );
        }
        return post;
    }

    private void printServerMessage(String message1, String message2) {
        String temp = "[ action: " + message1 + "\n" +
                "\"" + user.getUsername() + "\" " + message2 + "\n" +
                "time: " + LocalDateTime.now() + " ]\n";
        System.out.println(temp);
    }
}
