package syncthing.bep.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static syncthing.bep.util.Xdr.xdr;

public class XdrTests {

    @Test
    public void xdrEncodesStrings() {
        byte[] bytes = xdr("String One", "String Two");
        XdrInputStream in = new XdrInputStream(bytes);
        assertThat(in.readString(), is(equalTo("String One")));
        assertThat(in.readString(), is(equalTo("String Two")));
    }

}
