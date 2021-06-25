package Model.DataTypes.Post;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment implements Serializable {
    public static final long serialVersionUID = 741671746574174L;
    private final String text;
    private final String ownerUsername;
    private final String publishTime;

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
