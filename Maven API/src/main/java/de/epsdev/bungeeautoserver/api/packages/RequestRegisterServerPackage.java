package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RequestRegisterServerPackage extends Package {
    public RequestRegisterServerPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestRegisterServerPackage(int port, String type, int max_players){
        super("RequestRegisterServerPackage");
        add("key", EPS_API.key);
        add("port", port);
        add("max_players", max_players);
        add("type", type);
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
            InetSocketAddress socketAddress = InetSocketAddress.createUnresolved(String.valueOf(socket.getInetAddress())
                    ,getInteger("port"));

            int maxPlayers = getInteger("max_players");

            RemoteServer remoteServer = new RemoteServer(socketAddress, getString("type") ,maxPlayers);
        }
    }
}
