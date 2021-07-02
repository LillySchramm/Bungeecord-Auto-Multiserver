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

        add("password", EPS_API.key);
        add("name",EPS_API.NAME);
    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        if(ServerManager.verifyKey(getString("password"))){
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
