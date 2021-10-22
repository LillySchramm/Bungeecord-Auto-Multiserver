package de.epsdev.bungeeautoserver.api.interfaces;

import java.io.File;

public interface SyncEmitter {
    void scheduleSyncSave(File f, String content);
}
