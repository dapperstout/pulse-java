package syncthing.bep.util;

public class Bytes {

    public static byte leftNibble(byte twoNibbles) {
        return (byte) ((twoNibbles >> 4) & (byte) 0x0F);
    }

    public static byte rightNibble(byte twoNibbles) {
        return (byte) (twoNibbles & 0x0F);
    }

    public static byte leftByte(short twoBytes) {
        return (byte) ((twoBytes >> 8) & 0x00FF);
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
}
