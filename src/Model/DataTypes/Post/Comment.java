package Model.DataTypes.Post;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment implements Serializable {
    public static final long serialVersionUID = 600000000L;
    private String text;
    private String ownerUsername;
    private String publishTime;

    public Comment(String text, String ownerUsername, String publishTime) {
        this.text = text;
        this.ownerUsername = ownerUsername;
        this.publishTime = LocalDateTime.parse(publishTime).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getText() {
        return text;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getPublishTime() {
        return publishTime;
    }
}
