package syncthing.bep.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Xdr {

    public static byte[] xdr(String... strings) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XdrOutputStream xdr = new XdrOutputStream(out);
        try {
            for (String string : strings) {
                xdr.write(string);
            }
        } catch (IOException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
        return out.toByteArray();
    }
}
