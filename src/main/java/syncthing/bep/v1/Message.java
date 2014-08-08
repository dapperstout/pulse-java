package syncthing.bep.v1;

import static java.lang.System.arraycopy;
import static syncthing.bep.util.Bytes.*;

public class Message {

    private static final byte version = 0;
    private static short nextMessageId = 0;

    private final short id;
    private byte type;
    private boolean isCompressed = true;
    private byte[] contents = new byte[]{};

    static synchronized short getNextMessageId() {
        short result = nextMessageId;
        nextMessageId = (short) ((nextMessageId + 1) % 4096);
        return result;
    }

    public Message() {
        id = getNextMessageId();
    }

    public Message(byte type) {
        this();
        this.type = type;
    }

    public byte getVersion() {
        return version;
    }

    public short getId() {
        return id;
    }

    public boolean isCompressed() {
        return isCompressed;
    }

    public void setCompressed(boolean compressed) {
        this.isCompressed = compressed;
    }

    public byte[] getBytes() {
        byte[] result = new byte[8 + contents.length];
        result[0] = concatenateNibbles(version, bytes(id)[0]);
        result[1] = bytes(id)[1];
        result[2] = type;
        result[3] = concatenateBits(false, false, false, false, false, false, false, isCompressed);
        byte[] lengthBytes = bytes(contents.length);
        result[4] = lengthBytes[0];
        result[5] = lengthBytes[1];
        result[6] = lengthBytes[2];
        result[7] = lengthBytes[3];
        arraycopy(contents, 0, result, 8, contents.length);
        return result;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
}
