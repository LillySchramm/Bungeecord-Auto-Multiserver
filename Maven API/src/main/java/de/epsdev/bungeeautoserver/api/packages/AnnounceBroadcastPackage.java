package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.interfaces.BroadcastEmitter;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;

import java.net.Socket;

public class AnnounceBroadcastPackage extends Package {

    public static BroadcastEmitter broadcastEmitter;

    public AnnounceBroadcastPackage(Base_Package base_package) {
        super(base_package);
    }

    public AnnounceBroadcastPackage(String message) {
        super("AnnounceBroadcastPackage");

        add("message", message);

        for (Socket socket : EPS_API.sockets){
            try {
                send(socket);
            }catch (Exception ignored){}
        }
    }

    @Override
    public void onPackageReceive(Socket sender, Object o) {
        broadcastEmitter.onBroadcast(getString("message"));
    }
}
