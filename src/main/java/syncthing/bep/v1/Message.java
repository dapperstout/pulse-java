package syncthing.bep.v1;

import static syncthing.bep.util.Bytes.concatenateNibbles;
import static syncthing.bep.util.Bytes.leftByte;
import static syncthing.bep.util.Bytes.rightByte;

public class Message {

    private static final byte version = 0;
    private static short nextMessageId = 0;

    private final short id;
    private byte type;

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

    public byte[] getBytes() {
        byte[] result = new byte[3];
        result[0] = concatenateNibbles(version, leftByte(id));
        result[1] = rightByte(id);
        result[2] = type;

        return result;
    }
}
