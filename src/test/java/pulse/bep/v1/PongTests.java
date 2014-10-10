package pulse.bep.v1;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PongTests {

    private Ping ping;
    private Pong pong;

    @Before
    public void setUp() throws Exception {
        ping = new Ping();
        pong = new Pong(ping);
    }

    @Test
    public void hasType5() {
        assertThat(pong.getType(), is(equalTo((byte) 5)));
    }

    @Test
    public void isNotCompressed() {
        assertThat(pong.isCompressed(), is(false));
    }

    @Test
    public void hasSameMessageIdAsPing() {
        assertThat(pong.getId(), is(equalTo(ping.getId())));
    }
}
