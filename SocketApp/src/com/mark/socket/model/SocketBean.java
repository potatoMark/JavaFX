package com.mark.socket.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SocketBean {

	private final StringProperty socketIp;

	private final IntegerProperty socketPort;

    public SocketBean() {
        this(null,0);
    }

    public SocketBean(String ip, int port) {
        this.socketIp = new SimpleStringProperty(ip);
        this.socketPort = new SimpleIntegerProperty(port);
    }

    public String getSocketIp() {
        return socketIp.get();
    }

    public void setSocketIp(String socketIp) {
        this.socketIp.set(socketIp);
    }

    public int getSocketPort() {
        return socketPort.get();
    }

    public void setSocketPort(int socketPort) {
        this.socketPort.set(socketPort);
    }


}
