package syncthing.bep.v1;

import syncthing.bep.util.XdrOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Close extends Message {

    public Close(String someReason) {
        super((byte) 7, xdr(someReason), true);
    }

    private static byte[] xdr(String string) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XdrOutputStream xdr = new XdrOutputStream(out);
        try {
            xdr.write(string);
        } catch (IOException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
        return out.toByteArray();
    }
}
