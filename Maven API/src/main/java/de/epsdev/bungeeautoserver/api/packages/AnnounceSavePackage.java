package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;

import java.net.Socket;

public class AnnounceSavePackage extends Package {
    public AnnounceSavePackage(Base_Package base_package) {
        super(base_package);
    }

    public AnnounceSavePackage(String name, String origin) {
        super("AnnounceSavePackage");

        add("name", name);
        add("origin", origin);

        for (Socket socket : EPS_API.sockets){
            try {
                send(socket);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPackageReceive(Socket sender, Object o) {
        if(EPS_API.backupChannelName.equals(getString("world"))
                && !EPS_API.NAME.equals(getString("origin"))){
            EPS_API.backUpOutOfSync = true;
        }
    }
}
