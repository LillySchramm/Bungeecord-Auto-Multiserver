package de.epsdev.bungeeautoserver.api.interfaces;

import de.epsdev.bungeeautoserver.api.RemoteServer;

import java.net.InetSocketAddress;

public interface ServerStatusEmitter {
    void onConnect(String name, InetSocketAddress address);
    void onDisconnect(String name);
}
