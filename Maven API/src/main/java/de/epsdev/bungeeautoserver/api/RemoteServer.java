package de.epsdev.bungeeautoserver.api;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RemoteServer {
    private InetSocketAddress inetSocketAddress;

    private String name;
    private String type;

    private int max_players;

    public int timeout_ticks = 0;

    List<String> players = new ArrayList<>();

    Random rnd = new Random();

    public RemoteServer(InetSocketAddress inetSocketAddress, String type, int max_players){

        this.inetSocketAddress = inetSocketAddress;
        this.name = generateName();
        this.max_players = max_players;
        this.type = type;

        ServerManager.clearServerList(this.inetSocketAddress);
        ServerManager.addServer(this);
    }

    private String generateName(){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int len = 5;

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));

        return sb.toString();
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public String getName() {
        return name;
    }

    public int getMax_players() {
        return max_players;
    }

    public int getCurrent_players() {
        return this.players.size();
    }

    public String getType() {
        return type;
    }

    public String getStatus(){
        String ret = "";

        ret += "NAME: " + this.getName();
        ret += " PLAYERS: " + this.getCurrent_players() + "/" + this.max_players + "\n";

        for(String player : players){
            ret += EPS_API.PREFIX + "- " + player + "\n";
        }

        return ret;
    }
}
