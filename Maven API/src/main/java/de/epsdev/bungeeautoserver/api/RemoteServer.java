package de.epsdev.bungeeautoserver.api;

import java.net.InetSocketAddress;
import java.util.Random;

public class RemoteServer {
    private InetSocketAddress inetSocketAddress;

    private String name;
    private String type;

    private int max_players;
    private int current_players = 0;

    Random rnd = new Random();

    public RemoteServer(InetSocketAddress inetSocketAddress, String type ,int max_players){

        this.inetSocketAddress = inetSocketAddress;
        this.name = generateName();
        this.max_players = max_players;
        this.type = type;

        System.out.println(this.name);

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
        return current_players;
    }

    public void setCurrent_players(int current_players) {
        this.current_players = current_players;
    }

    public String getType() {
        return type;
    }
}
