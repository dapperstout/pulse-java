package pulse.bep.v1;

import org.junit.Before;
import org.junit.Test;
import pulse.bep.util.XdrInputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResponseTests {

    private static final byte[] SOME_DATA = new byte[]{ 1, 2, 3, 4};

    private Response response;
    private Request request;

    @Before
    public void setUp() {
        request = new Request("", "", 0, 0);
        response = new Response(request, SOME_DATA);
    }

    @Test
    public void hasType3() {
        assertThat(response.getType(), is(equalTo((byte)3)));
    }

    @Test
    public void hasXdrEncodedData() {
        XdrInputStream in = new XdrInputStream(response.getContents());
        assertThat(in.readData(), is(equalTo(SOME_DATA)));
    }

    @Test
    public void hasSameMessageIdAsRequest() {
        assertThat(response.getId(), is(equalTo(request.getId())));
    }
}
