package syncthing.bep.v1;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static syncthing.bep.util.Bytes.*;

public class MessageTests {

    @Test
    public void versionIsZero() {
        assertThat(new Message().getVersion(), is(equalTo((byte) 0)));
    }

    @Test
    public void idShouldBeUnique() {
        Set<Short> previousIds = new HashSet<>();
        for (int i = 0; i < 4096; i++) {
            short id = new Message().getId();
            assertThat(previousIds, not(hasItem(id)));
            previousIds.add(id);
        }
    }

    @Test
    public void versionIsEncodedInFirstNibble() {
        Message message = new Message();
        assertThat(leftNibble(message.getBytes()[0]), is(equalTo(message.getVersion())));
    }

    @Test
    public void idIsEncodedInSecondNibbleAndSecondByte() {
        Message message = new Message();
        byte[] bytes = message.getBytes();
        short id = concatenateBytes(rightNibble(bytes[0]), bytes[1]);
        assertThat(id, is(equalTo(message.getId())));
    }

    @Test
    public void typeIsEncodedInThirdByte() {
        byte type = 3;
        Message message = new Message(type);
        assertThat(message.getBytes()[2], is(equalTo(type)));
    }
}
