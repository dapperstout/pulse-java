package syncthing.bep.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static syncthing.bep.util.Xdr.xdr;

public class XdrInputStreamTests {

    @Test
    public void readsStrings() {
        byte[] xdrBytes = xdr("String");
        XdrInputStream in = new XdrInputStream(xdrBytes);

        assertThat(in.readString(), is(equalTo("String")));
    }

    @Test
    public void readsIntegers() {
        byte[] xdrBytes = xdr(0xF00FA00A);
        XdrInputStream in = new XdrInputStream(xdrBytes);

        assertThat(in.readInteger(), is(equalTo(0xF00FA00A)));
    }

    @Test
    public void readsLongs() {
        byte[] xdrBytes = xdr(0xF00FA00AB00BC00CL);
        XdrInputStream in = new XdrInputStream(xdrBytes);

        assertThat(in.readLong(), is(equalTo(0xF00FA00AB00BC00CL)));
    }

    @Test
    public void readsOpaqueData() {
        byte[] xdrBytes = xdr(new byte[]{12, 34});
        XdrInputStream in = new XdrInputStream(xdrBytes);

        assertThat(in.readData(), is(equalTo(new byte[]{12, 34})));
    }
}
