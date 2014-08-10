package syncthing.bep.v1;

import java.io.IOException;
import java.io.OutputStream;

import static syncthing.bep.util.Bytes.*;
import static syncthing.bep.util.LZ4Compression.compress;

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

    public Message(byte type, byte[] contents, boolean compress) {
        this.type = type;
        this.contents = compress ? compress(contents) : contents;
        this.isCompressed = compress;
    }

    public void writeTo(OutputStream out) throws IOException {
        byte[] idBytes = bytes(id);
        out.write(concatenateNibbles(VERSION, idBytes[0]));
        out.write(idBytes[1]);
        out.write(type);
        out.write(concatenateBits(false, false, false, false, false, false, false, isCompressed));
        out.write(bytes(contents.length));
        out.write(contents);
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

