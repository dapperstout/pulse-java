package syncthing.bep.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static syncthing.bep.util.Bytes.concatenateBytes;

public class XdrInputStream {

    private DataInputStream in;

    public XdrInputStream(byte[] xdrBytes) {
        in = new DataInputStream(new ByteArrayInputStream(xdrBytes));
    }

    public String readString() {
        try {
            return readStringThrowingIOException();
        } catch(IOException exception) {
            throw new IOFailed(exception);
        }
    }

    private String readStringThrowingIOException() throws IOException {
        int length = in.readInt();
        byte[] utf8Bytes = new byte[length];
        in.readFully(utf8Bytes);
        int amountOfPadding = 4 - (length % 4);
        for (int i = 0; i < amountOfPadding; i++) {
            in.readByte();
        }
        return new String(utf8Bytes, "UTF-8");
    }

    public int readInteger() {
        try {
            return readIntegerThrowingIOException();
        } catch(IOException exception) {
            throw new IOFailed(exception);
        }
    }

    private int readIntegerThrowingIOException() throws IOException {
        byte[] fourBytes = new byte[4];
        in.readFully(fourBytes);
        return concatenateBytes(fourBytes[0], fourBytes[1], fourBytes[2], fourBytes[3]);
    }

    public long readLong() {
        try {
            return readLongThrowingIOException();
        } catch(IOException exception) {
            throw new IOFailed(exception);
        }
    }

    private long readLongThrowingIOException() throws IOException {
        byte[] eightBytes = new byte[8];
        in.readFully(eightBytes);
        return concatenateBytes(eightBytes);
    }
}
