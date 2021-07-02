package de.epsdev.bungeeautoserver.api.config;

import de.epsdev.bungeeautoserver.api.EPS_API;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {
    public static boolean isBungeeReady(){
        return !patchServerProperties() && !patchSpigotYML();
    }

    private static boolean patchServerProperties(){
        File file = new File(System.getProperty("user.dir") + "\\server.properties");

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
        File file = new File(System.getProperty("user.dir") + "\\spigot.yml");

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
