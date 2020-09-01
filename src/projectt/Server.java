package projectt;

import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection {

    private String ip;
    private int port;

    public Server(String ip, int port, Consumer<Serializable> onRecieveCallback) {
        super(onRecieveCallback);
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
