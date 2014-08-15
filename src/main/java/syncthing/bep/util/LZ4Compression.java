package syncthing.bep.util;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.apache.commons.io.IOUtils.copy;

public class LZ4Compression {
    public static byte[] compress(byte[] data) {
        ByteArrayOutputStream compressedOutput = new ByteArrayOutputStream();
        LZ4BlockOutputStream compressor = new LZ4BlockOutputStream(compressedOutput);
        try {
            compressor.write(data);
            compressor.close();
        } catch (IOException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
        return compressedOutput.toByteArray();
    }

    public static byte[] decompress(byte[] data) {
        ByteArrayInputStream compressedInput = new ByteArrayInputStream(data);
        LZ4BlockInputStream decompressor = new LZ4BlockInputStream(compressedInput);
        ByteArrayOutputStream decompressedOutput = new ByteArrayOutputStream();
        try {
            copy(decompressor, decompressedOutput);
            decompressor.close();
        } catch (IOException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
        return decompressedOutput.toByteArray();
    }
}
