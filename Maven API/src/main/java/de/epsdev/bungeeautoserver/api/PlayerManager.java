package de.epsdev.bungeeautoserver.api;

import de.epsdev.bungeeautoserver.api.interfaces.PlayerStatusEmitter;

import java.util.HashMap;

public class PlayerManager {

    public static HashMap<String, String> players = new HashMap<>();
    public static PlayerStatusEmitter playerStatusEmitter;

    public static void changePlayerServer(String server, String playername){
        if(players.containsKey(playername)){
            ServerManager.removeFromServer(playername, players.get(playername));
            players.replace(playername, server);
        }else {
            players.put(playername, server);
        }

        playerStatusEmitter.onPlayerServerChange(playername);
    }

    public static void onPlayerDisconnect(String playername){
        ServerManager.removeFromServer(playername, players.get(playername));
        players.remove(playername);
    }


}
