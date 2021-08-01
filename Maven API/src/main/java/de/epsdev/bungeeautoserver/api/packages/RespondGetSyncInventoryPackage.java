package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.sync.SyncInventory;
import de.epsdev.bungeeautoserver.api.tools.SyncInventoryManagement;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;
import org.json.JSONObject;

import java.net.Socket;

public class RespondGetSyncInventoryPackage extends Package {
    public RespondGetSyncInventoryPackage(Base_Package base_package) {
        super(base_package);
    }

    public RespondGetSyncInventoryPackage(String channel_name, String player_uuid){
        super("RespondGetSyncInventoryPackage");

        SyncInventory syncInventory = SyncInventoryManagement.getInventory(channel_name, player_uuid);

        add("data", syncInventory.toJSONObject().toString());
    }

    @Override
    public void onPackageReceive(Socket sender, Object o) {
        SyncInventory syncInventory = new SyncInventory(new JSONObject(getString("data")));

        SyncInventoryManagement.emitter.sync(syncInventory);
    }
}
