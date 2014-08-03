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
    public void messageIdShouldBeUnique() {
        Set<Integer> previousMessageIds = new HashSet<>();
        for (int i = 0; i < 4096; i++) {
            int messageId = new Message().getId();
            assertThat(previousMessageIds, not(hasItem(messageId)));
            previousMessageIds.add(messageId);
        }
    }

    @Test
    public void versionIsEncodedInFirstNibble() {
        Message message = new Message();
        assertThat(leftNibble(message.getBytes()[0]), is(equalTo(message.getVersion())));
    }

    @Test
    public void messageIdIsEncodedInSecondNibbleAndSecondByte() {
        Message message = new Message();
        byte[] bytes = message.getBytes();
        short messageId = concatenateBytes(rightNibble(bytes[0]), bytes[1]);
        assertThat(messageId, is(equalTo(message.getId())));
    }

    @Test
    public void messageTypeIsEncodedInThirdByte() {
        byte messageType = 3;
        Message message = new Message(messageType);
        assertThat(message.getBytes()[2], is(equalTo((byte) 3)));
    }
}
