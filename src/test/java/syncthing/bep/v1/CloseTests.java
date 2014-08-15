package syncthing.bep.v1;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static syncthing.bep.util.Bytes.concatenateBytes;

public class CloseTests {

    public static final String SOME_REASON = "some reason";

    @Test
    public void isType7() {
        assertThat(new Close(SOME_REASON).getType(), is(equalTo((byte) 7)));
    }

    @Test
    public void hasXdrEncodedReason() {
        Close message = new Close(SOME_REASON);
        assertThat(extractReasonFromClose(message), is(equalTo(SOME_REASON)));
    }

    private String extractReasonFromClose(Close message) {
        byte[] contents = message.getContents();
        String reason;
        try {
            int reasonLength = concatenateBytes(contents[0], contents[1], contents[2], contents[3]);
            reason = new String(contents, 4, reasonLength, "UTF-8");
        } catch (UnsupportedEncodingException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
        return reason;
    }

    @Test
    public void xdrEncodedReasonEndsOnFourByteBoundary() {
        Close message = new Close(SOME_REASON);
        assertThat(message.getContents().length % 4, is(equalTo(0)));
    }
}
