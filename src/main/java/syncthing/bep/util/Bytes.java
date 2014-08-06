package syncthing.bep.util;

public class Bytes {

    public static byte leftNibble(byte twoNibbles) {
        return (byte) ((twoNibbles >>> 4) & (byte) 0x0F);
    }

    public static byte rightNibble(byte twoNibbles) {
        return (byte) (twoNibbles & 0x0F);
    }

    public static byte leftByte(short twoBytes) {
        return (byte) ((twoBytes >>> 8) & 0x00FF);
    }

    public static byte rightByte(short twoBytes) {
        return (byte) (twoBytes & 0x00FF);
    }

    public static short concatenateBytes(byte left, byte right) {
        return (short) ((unsigned(left) << 8) | unsigned(right));
    }

    public static byte concatenateNibbles(byte leftNibble, byte rightNibble) {
        return (byte) (leftNibble << 4 | rightNibble);
    }

    public static int unsigned(byte left) {
        return left & 0xFF;
    }

    public static boolean[] bits(byte eightBits) {
        boolean[] result = new boolean[8];
        byte mask = (byte) 0b10000000;
        for (int i=0; i<result.length; i++) {
            result[i] = (eightBits & mask) != 0;
            eightBits <<= 1;
        }
        return result;
    }

    public static byte bits(boolean... bits) {
        byte result = 0;
        for (boolean bit : bits) {
            if (result != 0) {
                result <<= 1;
            }
            if (bit) {
                result = (byte) (result | 1);
            }
        }
        return result;
    }
}
