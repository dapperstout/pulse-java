package syncthing.bep.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import static syncthing.bep.util.Bytes.bytes;

public class XdrOutputStream {
    private DataOutputStream out;

    public XdrOutputStream(OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    public void flush() {
        try {
            out.flush();
        } catch (IOException exception) {
            throw new IOFailed(exception);
        }
    }

    public void close() {
        try {
            out.close();
        } catch (IOException exception) {
            throw new IOFailed(exception);
        }
    }

    public void writeString(String string) {
        try {
            writeData(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
    }

    public void writeData(byte[] data) {
        try {
            writeDataThrowingIOException(data);
        } catch(IOException exception) {
            throw new IOFailed(exception);
        }
    }

    public void writeDataThrowingIOException(byte[] utf8Bytes) throws IOException {
        out.writeInt(utf8Bytes.length);
        out.write(utf8Bytes);
        int amountOfPadding = 4 - (utf8Bytes.length % 4);
        for (int i = 0; i < amountOfPadding; i++) {
            out.write(0);
        }
    }

    public void writeInteger(int i) {
        try {
            out.write(bytes(i));
        } catch (IOException exception) {
            throw new IOFailed(exception);
        }
    }

    public void writeLong(long l) {
        try {
            out.write(bytes(l));
        } catch (IOException exception) {
            throw new IOFailed(exception);
        }
    }
}
