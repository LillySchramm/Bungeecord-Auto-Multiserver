package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.bungeeautoserver.api.ServerInfo;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class RespondServerStatusPackage extends Package {
    public RespondServerStatusPackage(Base_Package base_package) {
        super(base_package);
    }

    public RespondServerStatusPackage(String type) {
        super("RespondServerStatusPackage");

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> max_players = new ArrayList<>();
        ArrayList<Integer> cur_players = new ArrayList<>();

        for(RemoteServer remoteServer : ServerManager.servers.getOrDefault(type, new ArrayList<>())){
            names.add(remoteServer.getName());
            max_players.add(remoteServer.getMax_players());
            cur_players.add(remoteServer.getCurrent_players());
        }

        add("names", names.toArray(new String[0]));
        add("max_players", max_players.toArray(new Integer[0]));
        add("cur_players", cur_players.toArray(new Integer[0]));
        add("type", type);
    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        ArrayList<ServerInfo> servers = new ArrayList();

        String[] names = getStringArray("names");
        Integer[] max_players = getIntegerArray("max_players");
        Integer[] cur_players = getIntegerArray("cur_players");

        for (int i = 0; i < names.length; i++) {
            servers.add(new ServerInfo(names[i], max_players[i], cur_players[i]));
        }

        EPS_API.serverInfo.put(getString("type"), servers);

    }
}
