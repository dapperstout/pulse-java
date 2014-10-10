package pulse.bep.v1;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PingTests {

    private Ping ping;

    @Before
    public void setUp() throws Exception {
        ping = new Ping();
    }

    @Test
    public void hasType4() {
        assertThat(ping.getType(), is(equalTo((byte) 4)));
    }

    @Test
    public void isNotCompressed() {
        assertThat(ping.isCompressed(), is(false));
    }
}
