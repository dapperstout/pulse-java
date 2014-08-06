package syncthing.bep.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static syncthing.bep.util.Bytes.*;

public class BytesTests {

    @Test
    public void testLeftAndRightNibbles() {
        byte twoNibbles = (byte) 0b10100101;
        assertThat(leftNibble(twoNibbles), is(equalTo((byte) 0b1010)));
        assertThat(rightNibble(twoNibbles), is(equalTo((byte) 0b0101)));
    }

    @Test
    public void testLeftAndRightBytes() {
        short twoBytes = (short) 0b1010101001010101;
        assertThat(leftByte(twoBytes), is(equalTo((byte) 0b10101010)));
        assertThat(rightByte(twoBytes), is(equalTo((byte) 0b01010101)));
    }

    @Test
    public void testUnsigned() {
        assertThat(unsigned((byte) 0b11111111), is(equalTo(255)));
    }

    @Test
    public void testNibbleConcatenation() {
        assertThat(concatenateNibbles((byte) 0b1010, (byte) 0b0101), is(equalTo((byte) 0b10100101)));
    }

    @Test
    public void testByteConcatenation() {
        byte leftByte = (byte) 0b10101010;
        byte rightByte = (byte) 0b01010101;
        assertThat(concatenateBytes(leftByte, rightByte), is(equalTo((short) 0b1010101001010101)));
    }

    @Test
    public void testDecompositionIntoBits() {
        boolean[] bits = bits((byte) 0b10101010);
        assertThat(bits, is(equalTo(new boolean[]{true, false, true, false, true, false, true, false})));
    }

    @Test
    public void testCompositionIntoByte() {
        boolean[] bits = new boolean[]{true, false, true, false, true, false, true, false};
        assertThat(bits(bits), is(equalTo((byte) 0b10101010)));
    }
}
