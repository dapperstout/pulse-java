package syncthing.bep.util;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class XdrInputStreamTests {

    public static final String SOME_STRING = "Some String";

    @Test
    public void readsStrings() throws IOException {
        byte[] xdrBytes = convertToXdr(SOME_STRING);

        XdrInputStream in = new XdrInputStream(xdrBytes);
        String xdrString = in.readString();

        assertThat(xdrString, is(equalTo(SOME_STRING)));
    }

    private byte[] convertToXdr(String string) {
        ByteArrayOutputStream xdrOutput = new ByteArrayOutputStream();
        XdrOutputStream out = new XdrOutputStream(xdrOutput);
        try {
            out.write(string);
        } catch (IOException shouldNeverHappen) {
            throw new Error(shouldNeverHappen);
        }
        return xdrOutput.toByteArray();
    }
}
