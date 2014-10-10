package pulse.bep.util;

import java.io.IOException;

public class IOFailed extends RuntimeException {
    public IOFailed(IOException cause) {
        super(cause);
    }
}
