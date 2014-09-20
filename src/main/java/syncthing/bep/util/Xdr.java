package syncthing.bep.util;

import java.io.ByteArrayOutputStream;

public class Xdr {

    public static byte[] xdr(String... strings) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XdrOutputStream xdr = new XdrOutputStream(out);
        for (String string : strings) {
            xdr.write(string);
        }
        return out.toByteArray();
    }
}
