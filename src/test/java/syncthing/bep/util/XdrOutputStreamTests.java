package syncthing.bep.util;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static java.util.Arrays.copyOfRange;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static syncthing.bep.util.Bytes.bytes;
import static syncthing.bep.util.Bytes.concatenateBytes;

public class XdrOutputStreamTests {

    private static final String SOME_STRING = "String with interesting unicode character \u221E";
    private static final byte[] SOME_DATA = new byte[]{12, 34};

    private XdrOutputStream out;
    private ByteArrayOutputStream wrapped;

    @Before
    public void setUp() {
        wrapped = spy(new ByteArrayOutputStream());
        out = new XdrOutputStream(wrapped);
    }

    @Test
    public void writesStringLength() throws UnsupportedEncodingException {
        out.writeString(SOME_STRING);

        byte[] xdrBytes = wrapped.toByteArray();
        int xdrStringLength = extractXdrDataLength(xdrBytes);
        assertThat(xdrStringLength, is(equalTo(SOME_STRING.getBytes("UTF-8").length)));
    }

    @Test
    public void writesString() throws UnsupportedEncodingException {
        out.writeString(SOME_STRING);

        byte[] xdrBytes = wrapped.toByteArray();
        String xdrString = new String(xdrBytes, 4, extractXdrDataLength(xdrBytes), "UTF-8");
        assertThat(xdrString, is(equalTo(SOME_STRING)));
    }

    @Test
    public void padsStringToFourByteBoundary() {
        out.writeString(SOME_STRING);

        byte[] xdrBytes = wrapped.toByteArray();
        assertThat(xdrBytes.length % 4, is(equalTo(0)));
    }

    @Test
    public void writesDataLength() {
        out.writeData(SOME_DATA);

        byte[] xdrBytes = wrapped.toByteArray();
        int xdrDataLength = extractXdrDataLength(xdrBytes);
        assertThat(xdrDataLength, is(equalTo(SOME_DATA.length)));
    }

    @Test
    public void writesData() {
        out.writeData(SOME_DATA);

        byte[] xdrBytes = wrapped.toByteArray();
        assertThat(copyOfRange(xdrBytes, 4, 4 + SOME_DATA.length), is(equalTo(SOME_DATA)));
    }

    private int extractXdrDataLength(byte[] bytes) {
        return concatenateBytes(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    @Test
    public void writesLong() {
        out.writeLong(0xF00FA00AB00BC00CL);

        byte[] xdrBytes = wrapped.toByteArray();
        assertThat(bytes(0xF00FA00AB00BC00CL), is(equalTo(xdrBytes)));
    }

    @Test
    public void writesInteger() {
        out.writeInteger(0xF00FA00A);

        byte[] xdrBytes = wrapped.toByteArray();
        assertThat(bytes(0xF00FA00A), is(equalTo(xdrBytes)));
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
