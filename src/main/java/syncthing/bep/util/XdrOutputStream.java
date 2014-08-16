package syncthing.bep.util;

import java.io.IOException;
import java.io.OutputStream;

import static syncthing.bep.util.Bytes.bytes;

public class XdrOutputStream {
    private OutputStream out;

    public XdrOutputStream(OutputStream out) {
        this.out = out;
    }

    public void flush() throws IOException {
        out.flush();
    }

    public void close() throws IOException {
        out.close();
    }

    public void write(String string) throws IOException {
        byte[] utf8Bytes = string.getBytes("UTF-8");
        out.write(bytes(utf8Bytes.length));
        out.write(utf8Bytes);
        int amountOfPadding = 4 - (utf8Bytes.length % 4);
        for (int i = 0; i < amountOfPadding; i++) {
            out.write(0);
        }
    }
}
