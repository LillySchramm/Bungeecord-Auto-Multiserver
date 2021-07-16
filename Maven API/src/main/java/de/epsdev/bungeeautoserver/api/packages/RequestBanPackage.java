package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.PlayerManager;
import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.ban.Ban;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RequestBanPackage extends Package {
    public RequestBanPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestBanPackage(String uuid, long until, String reason){
        super("RequestBanPackage");

        add("key", EPS_API.key);

        add("uuid", uuid);
        add("until", until);
        add("reason", reason);
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
            String uuid = getString("uuid");
            String reason = getString("reason");

            long until = getLong("reason");

            Ban ban = new Ban(uuid, until, reason);
            PlayerManager.playerStatusEmitter.onPlayerBanned(ban);
        }
    }
}
