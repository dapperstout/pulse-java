package pulse.bep.v1;

import org.junit.Before;
import org.junit.Test;
import pulse.bep.util.XdrInputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequestTests {

    private static final String SOME_REPOSITORY = "Some Repository";
    private static final String SOME_NAME = "Some Name";
    private static final long SOME_OFFSET = 42L;
    private static final int SOME_SIZE = 3;

    private Request message;

    @Before
    public void setUp() {
        message = new Request(SOME_REPOSITORY, SOME_NAME, SOME_OFFSET, SOME_SIZE);
    }

    @Test
    public void hasType2() {
        assertThat(message.getType(), is(equalTo((byte) 2)));
    }

    @Test
    public void hasXdrEncodedFields() {
        XdrInputStream in = new XdrInputStream(message.getContents());
        assertThat(in.readString(), is(equalTo(SOME_REPOSITORY)));
        assertThat(in.readString(), is(equalTo(SOME_NAME)));
        assertThat(in.readLong(), is(equalTo(SOME_OFFSET)));
        assertThat(in.readInteger(), is(equalTo(SOME_SIZE)));
    }
}
