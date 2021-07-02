package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.PlayerManager;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.Socket;

public class RequestChangePlayerServerPackage extends Package {
    public RequestChangePlayerServerPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestChangePlayerServerPackage(String server, String playername, boolean isSpecific){
        super("RequestChangePlayerServerPackage");

        add("server", server);
        add("playername", playername);
        add("isSpecific", isSpecific);
        add("password", EPS_API.key);
    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        if(!ServerManager.verifyKey(getString("password"))){
            try {
                new PackageServerError("Invalid key used.").send(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if(getBoolean("isSpecific")){
                PlayerManager.changePlayerServer(getString("server"), getString("playername"));
            }else {
                ServerManager.connectToServer(getString("server"), getString("playername"));
            }
        }
    }
}
