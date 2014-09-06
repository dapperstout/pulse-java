package syncthing.bep.v1;

import static syncthing.bep.util.Xdr.xdr;

public class Close extends Message {

    public Close(String someReason) {
        super((byte) 7, xdr(someReason), true);
    }
}
