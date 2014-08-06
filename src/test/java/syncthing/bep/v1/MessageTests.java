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
    public void compressionIsEnabledByDefault() {
        assertThat(new Message().isCompressed(), is(true));
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

    @Test
    public void reservedBitsAreZero() {
        boolean[] bits = bits(new Message().getBytes()[3]);
        for (int i = 0; i < 7; i++) {
            assertThat(bits[i], is(false));
        }
    }

    @Test
    public void compressionIsIndicatedInLastBitOfFourthByte() {
        for (boolean isCompressed : new boolean[]{true, false}) {
            Message message = new Message();
            message.setCompressed(isCompressed);
            boolean[] bits = bits(message.getBytes()[3]);
            assertThat(bits[7], is(isCompressed));
        }
    }
}
