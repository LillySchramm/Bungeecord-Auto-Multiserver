package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.bungeeautoserver.api.ServerInfo;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;

import java.net.Socket;
import java.util.ArrayList;

public class RespondServerStatusPackage extends Package {
    public RespondServerStatusPackage(Base_Package base_package) {
        super(base_package);
    }

    public RespondServerStatusPackage(String type) {
        super("RespondServerStatusPackage");

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> max_players = new ArrayList<>();
        ArrayList<Integer> cur_players = new ArrayList<>();

        for(RemoteServer remoteServer : ServerManager.servers.get(type)){
            names.add(remoteServer.getName());
            max_players.add(remoteServer.getMax_players());
            cur_players.add(remoteServer.getCurrent_players());
        }

        add("names", names.toArray());
        add("max_players", max_players.toArray());
        add("cur_players", cur_players.toArray());
    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        ArrayList<ServerInfo> servers = new ArrayList();

        String[] names = getStringArray("names");
        int[] max_players = getIntegerArray("max_players");
        int[] cur_players = getIntegerArray("cur_players");

        for (int i = 0; i < getStringArray("names").length; i++) {
            servers.add(new ServerInfo(names[i], max_players[i], cur_players[i]));
        }
    }
}
