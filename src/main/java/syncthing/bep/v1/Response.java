package syncthing.bep.v1;

import static syncthing.bep.util.Xdr.xdr;

public class Response extends Message {
    public Response(byte[] data) {
        super((byte)3, xdr(data));
    }
}
