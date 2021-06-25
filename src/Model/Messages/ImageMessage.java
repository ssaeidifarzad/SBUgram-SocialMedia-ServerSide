package Model.Messages;

import java.io.Serializable;

public class ImageMessage implements Serializable {
    public static final long serialVersionUID = 12370000896L;
    private final byte[] data;
    private final String format;

    public ImageMessage(byte[] data, String format) {
        this.data = data;
        this.format = format;
    }

    public byte[] getData() {
        return data;
    }

    public String getFormat() {
        return format;
    }
}
