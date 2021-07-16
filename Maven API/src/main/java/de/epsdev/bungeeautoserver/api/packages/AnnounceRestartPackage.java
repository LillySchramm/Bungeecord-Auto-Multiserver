package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.interfaces.RestartEmitter;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;

import java.net.Socket;

public class AnnounceRestartPackage extends Package {

    public static RestartEmitter restartEmitter;

    public AnnounceRestartPackage(Base_Package base_package) {
        super(base_package);
    }

    public AnnounceRestartPackage(){
        super("AnnounceRestartPackage");

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
        restartEmitter.restart();
    }
}
