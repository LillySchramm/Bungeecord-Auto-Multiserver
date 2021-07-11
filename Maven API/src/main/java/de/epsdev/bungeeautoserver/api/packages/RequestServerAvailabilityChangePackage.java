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

public class RequestServerAvailabilityChangePackage extends Package {
    public RequestServerAvailabilityChangePackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestServerAvailabilityChangePackage(boolean closed){
        super("RequestServerAvailabilityChangePackage");

        add("key", EPS_API.key);
        add("state", closed);
        add("server", EPS_API.NAME);

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
            boolean state = getBoolean("state");
            String server = getString("server");

            for (List<RemoteServer> servers : ServerManager.servers.values()) {
                for(RemoteServer rms : servers){
                    if(rms.getName().equals(server)){
                        rms.setClosed(state);
                        return;
                    }
                }
            }

        }
    }
}
