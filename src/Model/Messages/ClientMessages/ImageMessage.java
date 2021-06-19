package Model.Messages.ClientMessages;

public class ImageMessage implements ClientMessage {
    public static final long serialVersionUID = 1237896L;
    private byte[] data;
    private String format;

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
