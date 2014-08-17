package syncthing.bep.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class XdrInputStream {

    private DataInputStream in;

    public XdrInputStream(byte[] xdrBytes) {
        in = new DataInputStream(new ByteArrayInputStream(xdrBytes));
    }

    public String readString() throws IOException {
        int length = in.readInt();
        byte[] utf8Bytes = new byte[length];
        in.readFully(utf8Bytes);
        return new String(utf8Bytes, "UTF-8");
    }
}
