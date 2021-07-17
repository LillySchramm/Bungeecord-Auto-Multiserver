package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.PlayerManager;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.ban.Ban;
import de.epsdev.bungeeautoserver.api.tools.PlayerManagement;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.Socket;

public class RequestUnbanPackage extends Package {
    public RequestUnbanPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestUnbanPackage(String playername){
        super("RequestUnbanPackage");

        add("key", EPS_API.key);
        add("playername", playername);
    }

    @Override
    public void onPackageReceive(Socket sender, Object o) {
        if(!ServerManager.verifyKey(getString("key"))){
            try {
                new PackageServerError("Invalid key used.").send(sender);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            String playername = getString("playername");

            String uuid = PlayerManagement.getUUID(playername);

            Ban ban = Ban.getBanned(uuid);
            Ban.bans.remove(uuid);

            PlayerManager.playerStatusEmitter.onPlayerBanned(ban);
        }
    }
}
