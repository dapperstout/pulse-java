package syncthing.bep.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static syncthing.bep.util.Xdr.xdr;

public class XdrTests {

    @Test
    public void xdrEncodesIntermingledTypes() {
        byte[] bytes = xdr("String", 42L, 3);
        XdrInputStream in = new XdrInputStream(bytes);

        assertThat(in.readString(), is(equalTo("String")));
        assertThat(in.readLong(), is(equalTo(42L)));
        assertThat(in.readInteger(), is(equalTo(3)));
    }
}
