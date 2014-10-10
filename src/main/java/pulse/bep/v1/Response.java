package pulse.bep.v1;

import static pulse.bep.util.Xdr.xdr;

public class Response extends Message {
    public Response(Request request, byte[] data) {
        super(request.getId(), (byte)3, xdr(data), true);
    }
}
