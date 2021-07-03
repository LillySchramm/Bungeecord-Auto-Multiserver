package de.epsdev.bungeeautoserver.api.interfaces;

public interface PlayerStatusEmitter {
    void onPlayerServerChange(String playername, String servername);
}
