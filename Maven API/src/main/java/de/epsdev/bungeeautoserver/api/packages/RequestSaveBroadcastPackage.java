package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class RequestSaveBroadcastPackage extends Package {
    public RequestSaveBroadcastPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestSaveBroadcastPackage() {
        super("RequestSaveBroadcastPackage");

        add("world", EPS_API.backupChannelName);
        add("origin", EPS_API.NAME);
        add("key", EPS_API.key);
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
            new AnnounceSavePackage(getString("world"),getString("origin"));
        }
    }
}
