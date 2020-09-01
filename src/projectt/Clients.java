/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectt;

import java.io.Serializable;
import java.util.function.Consumer;

public class Clients extends NetworkConnection{
    private String ip;
    private int port;
    

    public Clients(String ip, int port, Consumer<Serializable> onRecieveCallback) {
        super(onRecieveCallback);
        this.ip = ip;
        this.port = port;
    }

    
    @Override
    protected boolean isServer() {
        return false;
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
