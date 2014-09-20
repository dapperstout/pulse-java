package syncthing.bep.v1;

import org.junit.Before;
import org.junit.Test;
import syncthing.bep.util.XdrInputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResponseTests {

    private static final byte[] SOME_DATA = new byte[]{ 1, 2, 3, 4};

    private Response message;

    @Before
    public void setUp() {
        message = new Response(SOME_DATA);
    }

    @Test
    public void hasType3() {
        assertThat(message.getType(), is(equalTo((byte)3)));
    }

    @Test
    public void hasXdrEncodedData() {
        XdrInputStream in = new XdrInputStream(message.getContents());
        assertThat(in.readData(), is(equalTo(SOME_DATA)));
    }
}
