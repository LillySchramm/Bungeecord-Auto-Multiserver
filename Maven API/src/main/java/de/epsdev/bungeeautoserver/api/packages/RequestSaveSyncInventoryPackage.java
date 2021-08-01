package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.sync.SyncInventory;
import de.epsdev.bungeeautoserver.api.tools.SyncInventoryManagement;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import de.epsdev.packages.packages.PackageServerError;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

public class RequestSaveSyncInventoryPackage extends Package {

    public RequestSaveSyncInventoryPackage(Base_Package base_package) {
        super(base_package);
    }

    public RequestSaveSyncInventoryPackage(String uuid) {
        super("RequestSaveSyncInventoryPackage");

        SyncInventory syncInventory = SyncInventoryManagement.emitter.getSyncInventoryServerSide(uuid);

        add("key", EPS_API.key);
        add("data", syncInventory.toJSONObject().toString());
        add("channel_name", SyncInventoryManagement.ChannelName);
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
            SyncInventory inventory = new SyncInventory(new JSONObject(getString("data")));
            SyncInventoryManagement.setInventory(getString("channel_name"), inventory);
        }
    }
}
