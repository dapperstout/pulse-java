package syncthing.bep.v1;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.arraycopy;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static syncthing.bep.util.Bytes.*;

public class MessageTests {

    @Test
    public void versionIsZero() {
        Message message = new SomeMessage();
        byte version = extractVersionFromMessage(message);
        assertThat(version, is(equalTo((byte) 0)));
    }

    private byte extractVersionFromMessage(Message message) {
        byte firstByte = serialize(message)[0];
        return nibbles(firstByte)[0];
    }

    @Test
    public void idIsUnique() {
        Set<Short> previousIds = new HashSet<>();
        for (int i = 0; i < 4096; i++) {
            Message message = new SomeMessage();
            short id = extractIdFromMessage(message);
            assertThat(previousIds, not(hasItem(id)));
            previousIds.add(id);
        }
    }

    private short extractIdFromMessage(Message message) {
        byte[] bytes = serialize(message);
        return concatenateBytes(nibbles(bytes[0])[1], bytes[1]);
    }

    @Test
    public void typeIsEncodedInThirdByte() {
        Message message = new Message((byte) 3);
        byte type = extractTypeFromMessage(message);
        assertThat(type, is(equalTo((byte) 3)));
    }

    private byte extractTypeFromMessage(Message message) {
        return serialize(message)[2];
    }

    @Test
    public void reservedBitsAreZero() {
        Message message = new SomeMessage();
        boolean[] bits = bits(serialize(message)[3]);
        for (int i = 0; i < 7; i++) {
            assertThat(bits[i], is(false));
        }
    }

    @Test
    public void compressionIsEnabledByDefault() {
        boolean isCompressed = extractIsCompressedFromMessage(new SomeMessage());
        assertThat(isCompressed, is(true));
    }

    @Test
    public void compressionIsIndicatedInLastBitOfFourthByte() {
        Message message = new Message((byte) 0, new byte[]{}, false);
        boolean isCompressed = extractIsCompressedFromMessage(message);
        assertThat(isCompressed, is(false));
    }

    private boolean extractIsCompressedFromMessage(Message message) {
        return bits(serialize(message)[3])[7];
    }

    @Test
    public void uncompressedLengthIsIndicatedInBytesFiveThroughEight() {
        Message message = new Message((byte) 0, new byte[123456], false);
        int length = extractContentLengthFromMessage(message);
        assertThat(length, is(equalTo(123456)));
    }

    private int extractContentLengthFromMessage(Message message) {
        byte[] bytes = serialize(message);
        return concatenateBytes(bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    @Test
    public void uncompressedContentsIsPresentInBytesNineAndForward() {
        Message message = new Message((byte) 0, new byte[]{12, 34, 56, 78}, false);
        byte[] contents = extractContentsFromMessage(message);
        assertThat(contents, is(equalTo(new byte[]{12, 34, 56, 78})));
    }

    private byte[] extractContentsFromMessage(Message message) {
        byte[] bytes = serialize(message);
        byte[] contents = new byte[bytes.length - 8];
        arraycopy(bytes, 8, contents, 0, contents.length);
        return contents;
    }

    private byte[] serialize(Message message) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            message.writeTo(out);
        } catch (IOException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
        return out.toByteArray();
    }

    private class SomeMessage extends Message {
        public SomeMessage() {
            super((byte) 0);
        }
    }
}
