package de.epsdev.bungeeautoserver.api.tools;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.config.Config;
import de.epsdev.bungeeautoserver.api.interfaces.FTPStatusEmitter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;
import java.util.ArrayList;

public class FTPManagement {

    public static final String BACKUP_DIRECTORY_NAME = "MINECRAFT_BACKUP";
    public static final String[] WORLD_FOLDER_NAMES = new String[]{"world", "world_nether", "world_the_end"};

    public static FTPStatusEmitter ftpStatusEmitter = new FTPStatusEmitter() {
        @Override
        public void startDownload(String directoryName) {}

        @Override
        public void startUpload(String directoryName) {}

        @Override
        public void finishDownload(long size) {}

        @Override
        public void finishUpload(long size) {}

        @Override
        public void finishTotal(long totalSize) {}
    };

    public static FTPSClient openFTPSClient(String serverAddress, int serverPort,
                                             String username, String password) throws IOException {
        FTPSClient client = new FTPSClient();

        //client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        client.connect(serverAddress, serverPort);
        client.execPROT("P");

        int reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        client.login(username, password);
        client.setFileType(FTP.BINARY_FILE_TYPE);

        return client;
    }

    private static void initFileStructure(FTPSClient ftpsClient) throws IOException {
        ftpsClient.makeDirectory(BACKUP_DIRECTORY_NAME);
        ftpsClient.changeWorkingDirectory(BACKUP_DIRECTORY_NAME);
        ftpsClient.makeDirectory(EPS_API.backupChannelName);

        ftpsClient.changeWorkingDirectory(EPS_API.backupChannelName);
        for (String folder : WORLD_FOLDER_NAMES) ftpsClient.makeDirectory(folder);
        ftpsClient.changeWorkingDirectory("../");
    }

    private static ArrayList<String> listFilesInRemoteDirectory(FTPSClient ftpsClient, String directory)
            throws IOException {
        ArrayList<String> files = new ArrayList<>();

        for (FTPFile file : ftpsClient.listFiles(directory)){
            if (file.isDirectory()) {
                files.addAll(listFilesInRemoteDirectory(ftpsClient,directory + "/" + file.getName()));
                continue;
            }

            files.add(directory + "/" + file.getName());
        }

        return files;
    }

    private static ArrayList<String>[] listFilesInLocalDirectory(File folder) throws IOException {
        ArrayList<String> files = new ArrayList<>();
        ArrayList<String> directories = new ArrayList<>();
        directories.add(folder.toString().replace("\\", "/"));

        if (folder.listFiles() == null) return new ArrayList[]{files, directories};

        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                ArrayList<String>[] __ = listFilesInLocalDirectory(fileEntry);
                files.addAll(__[0]);
                directories.addAll(__[1]);
            } else {
                files.add(folder + "/" + fileEntry.getName());
            }
        }

        return new ArrayList[]{files, directories};
    }


    public static void downloadWorld(String serverAddress, int serverPort,
                                     String username, String password) throws IOException {
        FTPSClient ftpsClient = openFTPSClient(serverAddress, serverPort, username, password);
        initFileStructure(ftpsClient);

        /*
        I initially planed to get the Checksums of the files and then only download the modified files. Problem is, that
        some FTP-Servers, vsFTPd for example, don't support the necessary commands.
         */

        ftpsClient.enterLocalPassiveMode();
        ftpsClient.changeWorkingDirectory(EPS_API.backupChannelName);

        long totalSize = 0;
        for (String folder : WORLD_FOLDER_NAMES) {
            File file = new File(folder);
            FileUtils.deleteDirectory(file);

            for (String remoteFile : listFilesInRemoteDirectory(ftpsClient, folder)){
                ftpStatusEmitter.startDownload(remoteFile);

                FileUtils.touch(new File(remoteFile));
                new FileOutputStream("./" + remoteFile);

                ftpsClient.retrieveFile(remoteFile,
                        new FileOutputStream("./" + remoteFile));

                long size = new File("./" + remoteFile).length();
                totalSize += size;
                ftpStatusEmitter.finishDownload(size);
            }
        }

        ftpStatusEmitter.finishTotal(totalSize);
        ftpsClient.disconnect();
    }

    public static void uploadWorld(String serverAddress, int serverPort,
                                     String username, String password) throws Exception {

        FTPSClient ftpsClient = openFTPSClient(serverAddress, serverPort, username, password);
        initFileStructure(ftpsClient);

        ftpsClient.enterLocalPassiveMode();

        // Create Temporary Folder
        ftpsClient.makeDirectory(EPS_API.backupChannelName + "_temp");
        ftpsClient.changeWorkingDirectory("./" + EPS_API.backupChannelName + "_temp");

        long totalSize = 0;
        for (String folder : WORLD_FOLDER_NAMES) {
            ArrayList<String>[] __ = listFilesInLocalDirectory(new File(folder));

            for (String _folder : __[1]) ftpsClient.makeDirectory("./" + _folder);

            for (String localFile : __[0]){
                ftpStatusEmitter.startUpload(localFile);

                localFile = localFile.replace("\\", "/");
                ftpsClient.storeFile("./" + localFile, new FileInputStream(localFile));

                long size = new File(localFile).length();
                totalSize += size;
                ftpStatusEmitter.finishUpload(size);
            }
        }

        ftpsClient.changeWorkingDirectory("../");
        deleteRemoteDirectory(ftpsClient, "./" + EPS_API.backupChannelName);
        ftpsClient.rename("./" + EPS_API.backupChannelName + "_temp", "./" + EPS_API.backupChannelName);

        ftpStatusEmitter.finishTotal(totalSize);
        ftpsClient.disconnect();
    }

    private static void deleteRemoteDirectory(FTPSClient ftpsClient, String path) throws IOException {
        FTPFile[] files= ftpsClient.listFiles(path);
        if (files.length > 0) {
            for (FTPFile ftpFile : files) {
                if (ftpFile.isDirectory()){
                    deleteRemoteDirectory(ftpsClient, path + "/" + ftpFile.getName());
                }
                else {
                    String deleteFilePath = path + "/" + ftpFile.getName();
                    ftpsClient.deleteFile(deleteFilePath);
                }
            }
        }

        ftpsClient.removeDirectory(path);
    }
}
