package Model.Messages;

import java.io.Serializable;

public class ImageMessage implements Serializable {
    public static final long serialVersionUID = 12370000896L;
    private final byte[] data;

    public ImageMessage(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}
