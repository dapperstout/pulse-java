package syncthing.bep.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static syncthing.bep.util.Bytes.concatenateBytes;
import static syncthing.bep.util.LZ4Compression.*;
import static syncthing.bep.util.LZ4Compression.compress;
import static syncthing.bep.util.LZ4Compression.decompress;

public class LZ4CompressionTests {

    @Test
    public void compressedOutputStartsWithUncompressedSize() {
        byte[] compressed = compress(SOME_DATA);
        int uncompressedSize = concatenateBytes(compressed[0], compressed[1], compressed[2], compressed[3]);
        assertThat(uncompressedSize, is(equalTo(SOME_DATA.length)));
    }

    @Test
    public void canDecompressCompressedData() {
        assertThat(decompress(compress(SOME_DATA)), is(equalTo(SOME_DATA)));
    }

    @Test
    public void canDecompressCompressedEmptyData() {
        assertThat(decompress(compress(new byte[]{})), is(equalTo(new byte[]{})));
    }

    @Test(expected = InvalidLZ4Data.class)
    public void shouldNotDecompressInvalidData() {
        decompress(new byte[]{0, 0, 0, 4, 12, 23, 56, 78});
    }

    @Test(expected = DecompressedDataTooLarge.class)
    public void shouldNotDecompressInvalidLength() {
        decompress(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, 12, 23, 56, 78});
    }

    private static final byte[] SOME_DATA = new byte[]{12, 34, 56, 78};

}
