package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.PlayerManager;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.ban.Ban;
import de.epsdev.bungeeautoserver.api.tools.PlayerManagement;
import de.epsdev.bungeeautoserver.api.tools.SyncInventoryManagement;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.Socket;

public class RequestGetSyncInventoryPackage extends Package {
    public RequestGetSyncInventoryPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestGetSyncInventoryPackage(String player_uuid) {
        super("RequestGetSyncInventoryPackage");

        add("key", EPS_API.key);

        add("channel_name", SyncInventoryManagement.ChannelName);
        add("player_uuid", player_uuid);
    }


    @Override
    public void onPackageReceive(Socket sender, Object o) {
        try {
            if(!ServerManager.verifyKey(getString("key"))){
                    new PackageServerError("Invalid key used.").send(sender);
            }else {
                String player_uuid = getString("player_uuid");
                String channel = getString("channel_name");

                new RespondGetSyncInventoryPackage(channel, player_uuid).send(sender);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
