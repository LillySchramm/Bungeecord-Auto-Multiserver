package de.epsdev.bungeeautoserver.api;

import de.epsdev.bungeeautoserver.api.interfaces.ServerStatusEmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerManager {
    public static HashMap<String, ArrayList<RemoteServer>> servers = new HashMap<>();
    public static ServerStatusEmitter statusEmitter;

    public static void addServer(RemoteServer remoteServer){
        if(servers.containsKey(remoteServer.getType())){
            servers.get(remoteServer.getType()).add(remoteServer);
        }else {
            ArrayList<RemoteServer> list = new ArrayList<>();
            list.add(remoteServer);
            servers.put(remoteServer.getType(), list);
        }

        statusEmitter.onConnect(remoteServer.getName(), remoteServer.getInetSocketAddress());
    }

    public static void removeServer(RemoteServer remoteServer){
        System.out.println(servers);
        servers.get(remoteServer.getType()).removeIf(server -> server.getName().equals(remoteServer.getName()));
        statusEmitter.onDisconnect(remoteServer.getName());
        System.out.println(servers);
    }

    public static boolean doesServerExist(String name){

        AtomicBoolean exists = new AtomicBoolean(false);

        servers.forEach((k,v) -> v.forEach((i) -> {
            if (i.getName().equalsIgnoreCase(name)) exists.set(true);
        }));

        return exists.get();
    }

    public static RemoteServer getRemoteServerByName(String name){
        for(ArrayList<RemoteServer> serverList : servers.values()){
            for(RemoteServer server : serverList){
                if(server.getName().equalsIgnoreCase(name)) return server;
            }
        }

        return null;
    }

    public static String connectToServer(String type, String playername){
        String ret = "null";
        if(!servers.containsKey(type)) return ret;

        int last_current = 0;
        for(RemoteServer remoteServer : servers.get(type)) {
            if(remoteServer.getCurrent_players() < remoteServer.getMax_players()){
                if(remoteServer.getCurrent_players() >= last_current){
                    last_current = remoteServer.getCurrent_players();
                    ret = remoteServer.getName();
                }
            }
        }

        if(!ret.equals("null")){
            getRemoteServerByName(ret).players.add(playername);

            PlayerManager.changePlayerServer(ret, playername);
        }

        printStatus();

        return ret;
    }

    public static void removeFromServer(String playername, String server){
        getRemoteServerByName(server).players.remove(playername);
        printStatus();
    }

    public static void printStatus(){

        System.out.println(EPS_API.PREFIX + "--------------------------------------------------");
        System.out.println(EPS_API.PREFIX + "                    STATUS                        ");
        System.out.println(EPS_API.PREFIX + "                                                  ");

        for(String key : servers.keySet()){
            System.out.println(EPS_API.PREFIX + "                " + key);
            System.out.println(EPS_API.PREFIX + " ");

            for (RemoteServer remoteServer : servers.get(key)){
                System.out.println(EPS_API.PREFIX + remoteServer.getStatus());
            }

            System.out.println(EPS_API.PREFIX + " ");
            System.out.println(EPS_API.PREFIX + " ");
        }

        System.out.println(EPS_API.PREFIX + "                                                  ");
        System.out.println(EPS_API.PREFIX + "--------------------------------------------------");
    }

    public static boolean verifyKey(String key){
        return (key.equals(EPS_API.key));
    }

}
