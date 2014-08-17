package syncthing.bep.util;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static syncthing.bep.util.Bytes.concatenateBytes;

public class XdrOutputStreamTests {

    private static final String SOME_STRING = "String with interesting unicode character \u221E";

    private XdrOutputStream out;
    private ByteArrayOutputStream wrapped;

    @Before
    public void setUp() throws Exception {
        wrapped = spy(new ByteArrayOutputStream());
        out = new XdrOutputStream(wrapped);
    }

    @Test
    public void writesStringLength() throws IOException {
        out.write(SOME_STRING);

        byte[] bytes = wrapped.toByteArray();
        int xdrStringLength = extractXdrStringLength(bytes);
        assertThat(xdrStringLength, is(equalTo(SOME_STRING.getBytes("UTF-8").length)));
    }

    @Test
    public void writesString() throws IOException {
        out.write(SOME_STRING);

        byte[] bytes = wrapped.toByteArray();
        String xdrString = new String(bytes, 4, extractXdrStringLength(bytes), "UTF-8");
        assertThat(xdrString, is(equalTo(SOME_STRING)));
    }

    @Test
    public void padsStringToFourByteBoundary() throws IOException {
        out.write(SOME_STRING);

        byte[] bytes = wrapped.toByteArray();
        assertThat(bytes.length % 4, is(equalTo(0)));
    }

    private int extractXdrStringLength(byte[] bytes) {
        return concatenateBytes(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    @Test
    public void forwardsFlushToWrappedStream() throws IOException {
        out.flush();

        verify(wrapped).flush();
    }

    @Test
    public void forwardsCloseToWrappedStream() throws IOException {
        out.close();

        verify(wrapped).close();
    }

}
