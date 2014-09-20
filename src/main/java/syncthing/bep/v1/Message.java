package syncthing.bep.v1;

import syncthing.bep.util.IOFailed;

import java.io.IOException;
import java.io.OutputStream;

import static syncthing.bep.util.Bytes.*;
import static syncthing.bep.util.LZ4Compression.compress;
import static syncthing.bep.util.LZ4Compression.decompress;

public class Message {

    private static final byte VERSION = 0;
    private short id;
    private byte type;
    private byte[] contents;
    private boolean isCompressed;

    public Message(byte type) {
        this(type, new byte[]{});
    }

    public Message(byte type, byte[] contents) {
        this(type, contents, true);
    }

    public Message(byte type, byte[] contents, boolean compress) {
        this(MessageId.getNextId(), type, contents, compress);
    }

    public Message(short id, byte type, byte[] contents, boolean compress) {
        this.id = id;
        this.type = type;
        this.contents = compress ? compress(contents) : contents;
        this.isCompressed = compress;
    }

    public short getId() {
        return id;
    }

    public byte getType() {
        return type;
    }

    public byte[] getContents() {
        return isCompressed ? decompress(contents) : contents;
    }

    public boolean isCompressed() {
        return isCompressed;
    }

    public void writeTo(OutputStream out) {
        try {
            writeThrowingIOException(out);
        } catch (IOException exception) {
            throw new IOFailed(exception);
        }
    }

    private void writeThrowingIOException(OutputStream out) throws IOException {
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

