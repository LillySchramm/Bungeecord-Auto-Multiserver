package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.config;

import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.GUI.Item_Config;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI_Config {

    private static File customConfigFile;
    private static FileConfiguration customConfig;

    private static List<Item_Config> items;

    public static void init(){
        createCustomConfig();

        customConfig.addDefault("heading", "");
        customConfig.addDefault("entries", new String[]{"s"});
        customConfig.options().copyDefaults(true);

        save();

    }

    private static void save(){
        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createCustomConfig()  {
        try {
            customConfigFile = new File(BungeecordAutoConfig.plugin.getDataFolder() + "/GUI.yml");
            if (!customConfigFile.exists()) {
                customConfigFile.createNewFile();
            }

            customConfig = new YamlConfiguration();

            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

