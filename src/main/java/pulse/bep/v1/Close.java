package pulse.bep.v1;

import static pulse.bep.util.Xdr.xdr;

public class Close extends Message {

    public Close(String someReason) {
        super((byte) 7, xdr(someReason), true);
    }
}
