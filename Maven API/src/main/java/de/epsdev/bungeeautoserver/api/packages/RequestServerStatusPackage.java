package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RequestServerStatusPackage extends Package {
    public RequestServerStatusPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestServerStatusPackage(String type){
        super("RequestServerStatusPackage");

        add("key", EPS_API.key);
        add("type", type);

    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        if(!ServerManager.verifyKey(getString("key"))){
            try {
                new PackageServerError("Invalid key used.").send(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                new RespondServerStatusPackage(getString("type")).send(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
