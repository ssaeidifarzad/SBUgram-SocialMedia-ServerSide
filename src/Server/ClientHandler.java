package Server;

import DataBase.Database;
import DataBase.UserDataHandler;
import Model.DataTypes.Post.Post;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.Post.RepostedPosts;
import Model.DataTypes.User.SafeUserData;
import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.*;
import Model.Messages.ImageMessage;
import Model.Messages.ServerMessages.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
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
                } else if (message instanceof ImageRequest) {
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
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (userDataHandler != null) {
                userDataHandler.updateData();
            }
        }
        if (user != null) {
            printServerMessage("quit");
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
                User temp = createNewUser();
                if (user.hasPhoto()) {
                    temp.setPhotoFormat(user.getPhotoFormat());
                }
                loginResponse.setUser(temp);
                printServerMessage("login");
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
                    signupRequest.getGender(),
                    signupRequest.isHasPhoto()
            );
            Database.getInstance().addUser(user);
            UserDataHandler udh = new UserDataHandler(user);
            if (signupRequest.isHasPhoto()) {
                try {
                    ImageMessage image = ((ImageMessage) objectInputStream.readObject());
                    udh.writeImage(image.getData(), image.getFormat());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            signupResponse.addResponse("success");
//            printServerMessage("register");
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
            user.setGender(editProfileRequest.getGender());
            user.setHasPhoto(editProfileRequest.isHasPhoto());
            response.setUser(createNewUser());
            Database.getInstance().getLoginData().put(user.getUsername(), user.getPassword());
            Database.getInstance().getUsers().put(user.getUsername(), user);
            if (editProfileRequest.isHasPhoto()) {
                try {
                    ImageMessage image = ((ImageMessage) objectInputStream.readObject());
                    userDataHandler.writeImage(image.getData(), image.getFormat());
                    user.setPhotoFormat(image.getFormat());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            printServerMessage("update info");
        }
        sendResponse(response);
    }

    private void publish(PublishRequest publishRequest) {
        PublishResponse publishResponse = new PublishResponse();
        Posts p = publishRequest.getPost();
        ((Post) p).setOwner(user);
        user.addPost(p);
        publishResponse.addResponse("success");
        publishResponse.setUser(createNewUser());
        printServerMessage("publish");
        sendResponse(publishResponse);
    }

    private void logout() {
        printServerMessage("logout");
        user = null;
        userDataHandler = null;
    }

    private void search(SearchRequest searchRequest) {
        SearchResponse searchResponse = new SearchResponse();
        if (Database.getInstance().getUsers().containsKey(searchRequest.getSearchedUsername())) {
            searchResponse.addResponse("success");
            searchResponse.setSafeUserData(createSafeUser(Database.getInstance().getUser(searchRequest.getSearchedUsername())));
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
        printServerMessage("follow");
    }

    private void unfollow(UnfollowRequest unfollowRequest) {
        user.unfollow(unfollowRequest.getSafeUserData().getUsername());
        Database.getInstance().getUser(unfollowRequest.getSafeUserData().getUsername())
                .getFollowers().remove(user);
        sendResponse(new UnfollowResponse(createNewUser()));
        printServerMessage("unfollow");
    }

    private void sendPosts() {
        Vector<Posts> posts = new Vector<>();
        for (User u : user.getFollowings()) {
            posts.addAll(createNewPosts(u.getPosts()));
        }
        sendResponse(new TimelinePostsResponse(posts));
    }

    private void repost(RepostRequest repostRequest) {
        User receivedUser = Database.getInstance().getUser(repostRequest.getPost().getOwner().getUsername());
        Posts receivedPost;
        if (repostRequest.getPost() instanceof RepostedPosts) {
            receivedPost = receivedUser.getAPost(((RepostedPosts) repostRequest.getPost()).getPost());
        } else {
             receivedPost = receivedUser.getAPost(repostRequest.getPost());
        }
        RepostedPosts rp = new RepostedPosts(receivedPost, user.getUsername());
        user.addPost(rp);
        receivedPost.repost(user.getUsername(), rp);
        sendResponse(new RepostResponse(createNewUser()));
        printServerMessage("repost");
    }

    private void like(LikeRequest likeRequest) {
        User receivedUser = Database.getInstance().getUser(likeRequest.getPost().getOwner().getUsername());
        Posts receivedPost;
        if (likeRequest.getPost() instanceof RepostedPosts) {
            receivedPost = receivedUser.getAPost(((RepostedPosts) likeRequest.getPost()).getPost());
        } else {
            receivedPost = receivedUser.getAPost(likeRequest.getPost());
        }
        receivedUser.getAPost(receivedPost).like(user.getUsername());
        sendResponse(new LikeResponse(createNewUser()));
        printServerMessage("like");
    }

    private void sendImage() {
        try {
            objectOutputStream.writeObject(new ImageMessage(userDataHandler.readImage(),
                    userDataHandler.getPhotoFormat()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendOtherProfileImage(User user) {
        UserDataHandler userDataHandler = new UserDataHandler(user);
        try {
            objectOutputStream.writeObject(new ImageMessage(userDataHandler.readImage(), userDataHandler.getPhotoFormat()));
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
                user.getGender(),
                user.hasPhoto(),
                new Vector<>(createNewPosts(user.getPosts())),
                new Vector<>(user.getFollowers()),
                new Vector<>(user.getFollowings())
        );
    }

    private SafeUserData createSafeUser(User user) {
        SafeUserData safeUser = new SafeUserData(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.hasPhoto(),
                createNewPosts(user.getPosts()),
                user.getFollowers().size(),
                user.getFollowings().size());
        if (user.hasPhoto())
            safeUser.setPhotoFormat(user.getPhotoFormat());
        return safeUser;
    }

    private Vector<Posts> createNewPosts(Vector<Posts> posts) {
        Vector<Posts> temp = new Vector<>();
        for (Posts p : posts) {
            if (p instanceof RepostedPosts) {
                temp.add(new RepostedPosts(((RepostedPosts) p).getPost(), ((RepostedPosts) p).getRepostUsername()));
            } else {
                temp.add(new Post(
                        p.getOwner(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getLikes(),
                        p.getReposts(),
                        p.getDateAndTime(),
                        p.getPublishTime()
                ));
            }
        }
        return temp;
    }

    private void printServerMessage(String message) {
        String temp = "[ action: " + message + "\n" +
                "\"" + user.getUsername() + "\" " + message + "\n" +
                "time: " + LocalDateTime.now() + " ]\n";
        System.out.println(temp);
    }
}
