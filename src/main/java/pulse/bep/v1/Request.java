package pulse.bep.v1;

import static pulse.bep.util.Xdr.xdr;

public class Request extends Message {

    public Request(String repository, String name, long offset, int size) {
        super((byte) 2, xdr(repository, name, offset, size));
    }
}
