package syncthing.bep.v1;

import org.junit.Before;
import org.junit.Test;
import syncthing.bep.util.XdrInputStream;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CloseTests {

    private static final String SOME_REASON = "some reason";

    private Close message;

    @Before
    public void setUp() throws Exception {
        message = new Close(SOME_REASON);
    }

    @Test
    public void isType7() {
        assertThat(message.getType(), is(equalTo((byte) 7)));
    }

    @Test
    public void hasXdrEncodedReason() {
        String reason = extractReasonFromClose(message);
        assertThat(reason, is(equalTo(SOME_REASON)));
    }

    private String extractReasonFromClose(Close message) {
        XdrInputStream in = new XdrInputStream(message.getContents());
        try {
            return in.readString();
        } catch (IOException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
    }

    @Test
    public void xdrEncodedReasonEndsOnFourByteBoundary() {
        int amountOfBytes = message.getContents().length;
        assertThat(amountOfBytes % 4, is(equalTo(0)));
    }
}
