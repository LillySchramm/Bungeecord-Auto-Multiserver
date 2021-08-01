package de.epsdev.bungeeautoserver.api.interfaces;

import de.epsdev.bungeeautoserver.api.sync.SyncInventory;

public interface SyncInventoryEventEmitter {
    void save();
    void sync(SyncInventory inventory);

    SyncInventory getSyncInventoryServerSide(String uuid);
}
