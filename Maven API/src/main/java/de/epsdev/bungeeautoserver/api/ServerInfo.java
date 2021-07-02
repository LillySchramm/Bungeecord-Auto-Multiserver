package de.epsdev.bungeeautoserver.api;

public class ServerInfo {

    public String name;
    public int maxPlayers;
    public int curPlayers;

    public ServerInfo(String name, int maxPlayers, int curPlayers){
        this.name = name;
        this.curPlayers = curPlayers;
        this.maxPlayers = maxPlayers;
    }
}
