package de.epsdev.bungeeautoserver.api;

import de.epsdev.bungeeautoserver.api.interfaces.ServerStatusEmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerManager {
    private static HashMap<String, ArrayList<RemoteServer>> servers = new HashMap<>();
    public static ServerStatusEmitter statusEmitter;

    public static void addServer(RemoteServer remoteServer){
        if(servers.containsKey(remoteServer.getType())){
            servers.get(remoteServer.getType()).add(remoteServer);
        }

        statusEmitter.onConnect(remoteServer.getName(), remoteServer.getInetSocketAddress());
    }

    public static void removeServer(RemoteServer remoteServer){
        servers.get(remoteServer.getType()).removeIf(server -> server.getName().equals(remoteServer.getName()));
        statusEmitter.onDisconnect(remoteServer.getName());
    }

    public static boolean doesServerExist(String name){

        AtomicBoolean exists = new AtomicBoolean(false);

        servers.forEach((k,v) -> v.forEach((i) -> {
            if (i.getName().equalsIgnoreCase(name)) exists.set(true);
        }));

        return exists.get();
    }

    public static RemoteServer getRemoteServerByName(String name){
        for(List<RemoteServer> serverList : servers.values()){
            for(RemoteServer server : serverList){
                if(server.getName().equalsIgnoreCase(name)) return server;
            }
        }

        return null;
    }

}
