package de.epsdev.bungeeautoserver.api.tools;

import de.epsdev.bungeeautoserver.api.EPS_API;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class FTPManagement {

    public static final String BACKUP_DIRECTORY_NAME = "MINECRAFT_BACKUP";
    public static final String[] WORLD_FOLDER_NAMES = new String[]{"world", "world_nether", "world_the_end"};

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

        return client;
    }

    private static void initFileStructure(FTPSClient ftpsClient) throws IOException {
        ftpsClient.makeDirectory(BACKUP_DIRECTORY_NAME);
        ftpsClient.changeWorkingDirectory(BACKUP_DIRECTORY_NAME);
        ftpsClient.makeDirectory(EPS_API.backupChannelName);
        ftpsClient.changeWorkingDirectory(EPS_API.backupChannelName);

        for (String folder : WORLD_FOLDER_NAMES) ftpsClient.makeDirectory(folder);
    }

    private static ArrayList<String> listFilesInDirectory(FTPSClient ftpsClient, String directory) throws IOException {
        ArrayList<String> files = new ArrayList<>();

        for (FTPFile file : ftpsClient.listFiles(directory)){
            if (file.isDirectory()) {
                files.addAll(listFilesInDirectory(ftpsClient,directory + "/" + file.getName()));
                continue;
            }

            files.add(directory + "/" + file.getName());
        }

        return files;
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

        for (String folder : WORLD_FOLDER_NAMES) {
            File file = new File(folder);
            FileUtils.deleteDirectory(file);

            for (String remoteFile : listFilesInDirectory(ftpsClient, folder)){
                FileUtils.touch(new File(remoteFile));
                new FileOutputStream("./" + remoteFile);

                ftpsClient.retrieveFile(remoteFile,
                        new FileOutputStream("./" + remoteFile));
            }
        }

        ftpsClient.disconnect();
    }

}
