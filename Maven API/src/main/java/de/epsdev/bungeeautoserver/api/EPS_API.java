package de.epsdev.bungeeautoserver.api;

import de.epsdev.bungeeautoserver.api.enums.OperationType;
import de.epsdev.bungeeautoserver.api.exeptions.NoPortDefinedException;
import de.epsdev.bungeeautoserver.api.exeptions.NoRemoteAddressException;
import de.epsdev.bungeeautoserver.api.packages.*;
import de.epsdev.packages.Connection;
import de.epsdev.packages.Server;
import de.epsdev.packages.packages.Package;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EPS_API {

    public static final String PREFIX = "[Auto Bungee] ";

    public static String NAME = "iAmADefault";
    public static String DEFAULT_SERVER = "Hub";

    private final OperationType operationType;
    private String remoteAddress;
    public Server server;
    public Connection connection;
    private int port = -1;
    private int max_players = 2;
    private String type = "Hub";

    public static List<Socket> sockets = new ArrayList<>();

    public static HashMap<String, ArrayList<ServerInfo>> serverInfo = new HashMap<>();

    public static String key = "";

    public EPS_API(OperationType operationType){
        this.operationType = operationType;
    }

    public void setRemoteAddress(String remoteAddress){
        this.remoteAddress = remoteAddress;
    }

    public void setPort(int port){
        this.port = port;
    }

    public void setMax_players(int max_players){
        this.max_players = max_players;
    }

    public void setType(String type){
        this.type = type;
    }

    public void init(){
        Package.registerPackage("RequestRegisterServerPackage", RequestRegisterServerPackage.class);
        Package.registerPackage("RequestChangePlayerServerPackage", RequestChangePlayerServerPackage.class);
        Package.registerPackage("RequestServerStatusPackage", RequestServerStatusPackage.class);
        Package.registerPackage("RequestServerAvailabilityChangePackage", RequestServerAvailabilityChangePackage.class);
        Package.registerPackage("RequestBroadcastPackage", RequestBroadcastPackage.class);
        Package.registerPackage("RequestBanPackage", RequestBanPackage.class);
        Package.registerPackage("RequestUnbanPackage", RequestUnbanPackage.class);
        Package.registerPackage("RequestSaveSyncInventoryPackage", RequestSaveSyncInventoryPackage.class);
        Package.registerPackage("RequestGetSyncInventoryPackage", RequestGetSyncInventoryPackage.class);

        Package.registerPackage("RespondRegisterPackage", RespondRegisterPackage.class);
        Package.registerPackage("RespondServerStatusPackage", RespondServerStatusPackage.class);
        Package.registerPackage("RespondGetSyncInventoryPackage", RespondGetSyncInventoryPackage.class);

        Package.registerPackage("AnnounceBroadcastPackage", AnnounceBroadcastPackage.class);
        Package.registerPackage("AnnounceRestartPackage", AnnounceRestartPackage.class);

        if(this.operationType == OperationType.SERVER){

            if(key.equals("")){
                System.out.println("------------------------------------------------------------");
                System.out.println("|                        WARNING                           |");
                System.out.println("|                                                          |");
                System.out.println("|No key used. NOT save to use in an production environment.|");
                System.out.println("|                                                          |");
                System.out.println("|                                                          |");
                System.out.println("------------------------------------------------------------");
            }

            this.server = new Server(10101, 512, true);
            this.server.start();

        }else if(this.operationType == OperationType.CLIENT){
            try {
                if(this.remoteAddress.equals("")){
                    throw new NoRemoteAddressException();
                }

                if(this.port == -1){
                    throw new NoPortDefinedException();
                }

                this.connection = new Connection(remoteAddress, 10101);
                this.connection.start();

                this.connection.send(new RequestRegisterServerPackage(this.port, this.type, this.max_players));

            } catch (NoRemoteAddressException | NoPortDefinedException e) {
                e.printStackTrace();
            }
        }
    }

    public void disable(){
        connection.close();
    }
}
