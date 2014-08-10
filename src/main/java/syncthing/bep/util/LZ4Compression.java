package syncthing.bep.util;

import net.jpountz.lz4.LZ4BlockOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
}
