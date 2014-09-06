package syncthing.bep.util;

public class Bytes {

    public static boolean[] bits(byte eightBits) {
        boolean[] result = new boolean[8];
        byte mask = (byte) 0b10000000;
        for (int i = 0; i < result.length; i++) {
            result[i] = (eightBits & mask) != 0;
            eightBits <<= 1;
        }
        return result;
    }

    public static byte[] nibbles(byte twoNibbles) {
        byte[] result = new byte[2];
        result[0] = (byte) ((twoNibbles >>> 4) & (byte) 0x0F);
        result[1] = (byte) (twoNibbles & 0x0F);
        return result;
    }

    public static byte[] bytes(short twoBytes) {
        byte[] result = new byte[2];
        result[0] = (byte) ((twoBytes >>> 8) & 0xFF);
        result[1] = (byte) (twoBytes & 0xFF);
        return result;
    }

    public static byte[] bytes(int fourBytes) {
        byte[] result = new byte[4];
        result[0] = (byte) ((fourBytes >>> 24) & 0xFF);
        result[1] = (byte) ((fourBytes >>> 16) & 0xFF);
        result[2] = (byte) ((fourBytes >>> 8) & 0xFF);
        result[3] = (byte) (fourBytes & 0xFF);
        return result;
    }

    public static byte concatenateBits(boolean... bits) {
        byte result = 0;
        for (boolean bit : bits) {
            result <<= 1;
            if (bit) {
                result = (byte) (result | 1);
            }
        }
        return result;
    }

    public static byte concatenateNibbles(byte leftNibble, byte rightNibble) {
        return (byte) (leftNibble << 4 | rightNibble);
    }

    public static short concatenateBytes(byte left, byte right) {
        return (short) ((unsigned(left) << 8) | unsigned(right));
    }

    public static int concatenateBytes(byte b0, byte b1, byte b2, byte b3) {
        return (unsigned(b0) << 24) | (unsigned(b1) << 16) | (unsigned(b2) << 8) | unsigned(b3);
    }

    public static int unsigned(byte b) {
        return b & 0xFF;
    }

    public static long unsigned(int i) {
        return i & 0xFFFFFFFFL;
    }
}
