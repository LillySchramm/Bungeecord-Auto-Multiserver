package de.epsdev.bungeeautoserver.api.config;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.tools.VersionManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {
    public static boolean CheckServerVersion = true;

    public static boolean isBungeeReady(){
        return !patchServerProperties() && !patchSpigotYML();
    }
    public static boolean isBungeeServerReady(){
        return !patchBungeeConfig();
    }

    public static boolean checkUpdate(String filename, String sha_url, String download_url){

        File file = new File(System.getProperty("user.dir") + "/server.sh");

        // TODO: if(!CheckServerVersion) return true; once debugging ended
        if(CheckServerVersion) return true;

        if(file.exists() && readFile(file).get(0).contains("Installed via script")){
            System.out.println(EPS_API.PREFIX + "Checking for updates... ");

            try {
                String sha512 = VersionManagement.getSHA512(new File(System.getProperty("user.dir") + "/" + filename + ".jar"));
                String check_sha512 = VersionManagement.getSHA512_URL(sha_url);

                if(sha512.equals(check_sha512)){
                    System.out.println(EPS_API.PREFIX + "Everything is up to date. Have a nice day.");
                }else {
                    System.out.println(EPS_API.PREFIX + "The installed version is outdated. Updating.... ");

                    VersionManagement.downloadAndReplace(filename, download_url);

                    return false;
                }

            } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();

                return true;
            }

        }else{
            System.out.println(EPS_API.PREFIX + "Not installed via script. Skipping autoupdatesss.");
        }

        return true;
    }

    private static boolean patchServerProperties(){
        File file = new File(System.getProperty("user.dir") + "/server.properties");

        String newContent = "";
        boolean changeNeeded = false;
        for (String line : readFile(file)){
            if(line.contains("online-mode=true")){
                line = "online-mode=false";
                changeNeeded = true;
                System.out.println(EPS_API.PREFIX + "Had to update server.properties");
            }

            newContent += line + "\n";
        }

        writeFile(file, newContent);

        return changeNeeded;
    }

    private static boolean patchSpigotYML(){
        File file = new File(System.getProperty("user.dir") + "/spigot.yml");

        String newContent = "";
        boolean changeNeeded = false;
        for (String line : readFile(file)){
            if(line.equalsIgnoreCase("  bungeecord: false")){
                line = "  bungeecord: true";
                changeNeeded = true;
                System.out.println(EPS_API.PREFIX + "Had to update spigot.yml");
            }

            newContent += line + "\n";
        }

        writeFile(file, newContent);
        return changeNeeded;
    }

    private static boolean patchBungeeConfig(){
        File file = new File(System.getProperty("user.dir") + "/config.yml");

        String newContent = "";
        boolean changeNeeded = false;
        for (String line : readFile(file)){
            if(line.contains("ip_forward: false")){
                line = "ip_forward: true";
                changeNeeded = true;
                System.out.println(EPS_API.PREFIX + "Had to update config.yml");
            }

            if(line.contains("online_mode: false")){
                line = "online_mode: true";
                changeNeeded = true;
                System.out.println(EPS_API.PREFIX + "Had to update config.yml");
            }

            if(line.contains("host: 0.0.0.0:25577")){
                line = "  host: 0.0.0.0:25565";
                changeNeeded = true;
                System.out.println(EPS_API.PREFIX + "Had to update config.yml");
            }

            if(line.contains("    address: localhost:25565")){
                line = "    address: localhost:25566";
                changeNeeded = true;
                System.out.println(EPS_API.PREFIX + "Had to update config.yml");
            }
            newContent += line + "\n";
        }

        writeFile(file, newContent);
        return changeNeeded;
    }

    private static List<String> readFile(File f){
        List<String> lines = new ArrayList<>();

        Scanner myReader = null;
        try {
            myReader = new Scanner(f);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private static void writeFile(File f, String content){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(f);
            writer.print(content);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
