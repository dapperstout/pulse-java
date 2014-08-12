package syncthing.bep.v1;

public class Pong extends Message {

    public Pong(Ping ping) {
        super(ping.getId(), (byte) 5, new byte[]{}, false);
    }
}
