package syncthing.bep.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

    public void write(String string) {
        try {
            writeThrowingIOException(string);
        } catch(IOException exception) {
            throw new IOFailed(exception);
        }
    }

    private void writeThrowingIOException(String string) throws IOException {
        byte[] utf8Bytes = string.getBytes("UTF-8");
        out.writeInt(utf8Bytes.length);
        out.write(utf8Bytes);
        int amountOfPadding = 4 - (utf8Bytes.length % 4);
        for (int i = 0; i < amountOfPadding; i++) {
            out.write(0);
        }
    }
}
