package pulse.bep.v1;

public class Ping extends Message {

    public Ping() {
        super((byte) 4, new byte[]{}, false);
    }
}
