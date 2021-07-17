package de.epsdev.bungeeautoserver.api.interfaces;

import de.epsdev.bungeeautoserver.api.ban.Ban;

public interface PlayerStatusEmitter {
    void onPlayerServerChange(String playername, String servername);
    void onPlayerBanned(Ban ban);
    void onPlayerUnbanned(Ban ban);
}
