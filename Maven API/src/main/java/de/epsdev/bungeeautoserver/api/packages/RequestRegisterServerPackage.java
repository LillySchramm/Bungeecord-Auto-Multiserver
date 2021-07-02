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
        add("password", EPS_API.key);
        add("port", port);
        add("max_players", max_players);
        add("type", type);
    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        System.out.println("REQ:" + getString("password"));
        System.out.println("BSE" + EPS_API.key);
        if(!getString("password").equals(EPS_API.key) || !EPS_API.key.equals("")){
            try {
                new PackageServerError("Invalid key used.").send(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            InetSocketAddress socketAddress = new InetSocketAddress(String.valueOf(socket.getInetAddress()).replace("/","")
                    ,getInteger("port"));

            int maxPlayers = getInteger("max_players");

            RemoteServer remoteServer = new RemoteServer(socketAddress, getString("type") ,maxPlayers);

            try {
                new RespondRegisterPackage(remoteServer.getName()).send(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
