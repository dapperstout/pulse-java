package syncthing.bep.v1;

import static java.lang.System.arraycopy;
import static syncthing.bep.util.Bytes.*;

public class Message {

    private static final byte VERSION = 0;
    private short id = MessageId.getNextId();
    private byte type;
    private boolean isCompressed = true;
    private byte[] contents = new byte[]{};

    public Message(byte type) {
        this(type, new byte[]{});
    }

    public Message(byte type, byte[] contents) {
        this(type, contents, true);
    }

    public Message(byte type, byte[] contents, boolean isCompressed) {
        this.type = type;
        this.contents = contents;
        this.isCompressed = isCompressed;
    }

    public byte[] getBytes() {
        byte[] result = new byte[8 + contents.length];
        byte[] idBytes = bytes(id);
        byte[] lengthBytes = bytes(contents.length);
        result[0] = concatenateNibbles(VERSION, idBytes[0]);
        result[1] = idBytes[1];
        result[2] = type;
        result[3] = concatenateBits(false, false, false, false, false, false, false, isCompressed);
        result[4] = lengthBytes[0];
        result[5] = lengthBytes[1];
        result[6] = lengthBytes[2];
        result[7] = lengthBytes[3];
        arraycopy(contents, 0, result, 8, contents.length);
        return result;
    }

    private static class MessageId {
        private static short nextId = 0;
        public static synchronized short getNextId() {
            short result = nextId;
            nextId = (short) ((nextId + 1) % 4096);
            return result;
        }
    }
}

