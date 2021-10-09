package de.epsdev.bungeeautoserver.api.interfaces;

public interface FTPStatusEmitter {
    void startDownload(String directoryName);
    void startUpload(String directoryName);

    void finishDownload(long size);
    void finishUpload(long size);

    void finishTotal(long totalSize);
}
