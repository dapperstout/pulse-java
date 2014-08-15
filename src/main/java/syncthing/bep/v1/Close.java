package syncthing.bep.v1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static syncthing.bep.util.Bytes.bytes;

public class Close extends Message {

    public Close(String someReason) {
        super((byte) 7, xdr(someReason), true);
    }

    private static byte[] xdr(String string) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] stringBytes = string.getBytes("UTF-8");
            out.write(bytes(stringBytes.length));
            out.write(stringBytes);
            int amountOfPadding = 4 - (stringBytes.length % 4);
            for (int i=0; i< amountOfPadding; i++) {
                out.write(0);
            }
        } catch (IOException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
        return out.toByteArray();
    }
}
