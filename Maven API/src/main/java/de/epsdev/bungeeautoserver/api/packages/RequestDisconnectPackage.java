package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.Socket;

public class RequestDisconnectPackage extends Package {
    public RequestDisconnectPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestDisconnectPackage(String name){
        super("RequestDisconnectPackage");

        add("key", EPS_API.key);
        add("name",EPS_API.NAME);
    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        if(!getString("key").equals(EPS_API.key) && !EPS_API.key.equals("")){
            try {
                new PackageServerError("Invalid key used.").send(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("NAME " + getString("name") );
            ServerManager.removeServer(ServerManager.getRemoteServerByName(getString("name")));
        }
    }
}
