package pulse.bep.util;

import java.io.ByteArrayOutputStream;

public class Xdr {

    public static byte[] xdr(Object... objects) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XdrOutputStream xdr = new XdrOutputStream(out);
        for (Object object : objects) {
            if (object instanceof String) {
                xdr.writeString((String) object);
            } else if (object instanceof Long) {
                xdr.writeLong((Long) object);
            } else if (object instanceof Integer) {
                xdr.writeInteger((Integer) object);
            } else if (object instanceof byte[]) {
                xdr.writeData((byte[]) object);
            } else {
                throw new UnexpectedObject(object);
            }
        }
        return out.toByteArray();
    }

    public static byte[] xdr(byte[] data) {
        return xdr((Object)data);
    }

    public static class UnexpectedObject extends RuntimeException {
        public UnexpectedObject(Object object) {
            super("Unexpected object: " + object);
        }
    }
}
