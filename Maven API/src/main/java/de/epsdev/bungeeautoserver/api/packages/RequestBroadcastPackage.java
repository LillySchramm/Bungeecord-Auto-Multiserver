package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.PlayerManager;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.Socket;

public class RequestBroadcastPackage extends Package {


    public RequestBroadcastPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestBroadcastPackage(String message){
        super("RequestBroadcastPackage");

        add("message", message);
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
            new AnnounceBroadcastPackage(getString("message"));
        }
    }
}
