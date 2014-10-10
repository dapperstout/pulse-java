package pulse.bep.util;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOf;
import static pulse.bep.util.Bytes.*;

public class LZ4Compression {

    private static final int MAX_BUFFER_LENGTH = 8 * 1024 * 1024;

    private static LZ4Factory lz4Factory = LZ4Factory.fastestInstance();
    private static LZ4Compressor compressor = lz4Factory.fastCompressor();
    private static LZ4FastDecompressor decompressor = lz4Factory.fastDecompressor();

    public static byte[] compress(byte[] data) {
        int maxCompressedLength = compressor.maxCompressedLength(data.length);
        byte[] compressed = new byte[4 + maxCompressedLength];
        arraycopy(bytes(data.length), 0, compressed, 0, 4);
        int compressedLength = compressor.compress(data, 0, data.length, compressed, 4);
        return copyOf(compressed, 4 + compressedLength);
    }

    public static byte[] decompress(byte[] data) {
        long decompressedLength = unsigned(concatenateBytes(data[0], data[1], data[2], data[3]));
        if (decompressedLength > MAX_BUFFER_LENGTH) {
            throw new DecompressedDataTooLarge();
        }
        try {
            return decompressor.decompress(data, 4, (int)decompressedLength);
        } catch(LZ4Exception exception) {
            throw new InvalidLZ4Data(exception);
        }
    }

    public static class InvalidLZ4Data extends RuntimeException {
        public InvalidLZ4Data(Throwable cause) {
            super(cause);
        }
    }

    public static class DecompressedDataTooLarge extends RuntimeException {
        public DecompressedDataTooLarge() {
            super("Decompressed data exceeds maximum size");
        }
    }
}
