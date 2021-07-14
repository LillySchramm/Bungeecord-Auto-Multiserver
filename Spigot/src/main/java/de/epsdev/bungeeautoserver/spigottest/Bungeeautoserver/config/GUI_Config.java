package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.config;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.GUI.Item_Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GUI_Config {

    private static File customConfigFile;
    private static FileConfiguration customConfig;

    public static HashMap<Integer, Item_Config> items_map = new HashMap<>();
    public static int rows;
    private static String heading;


    public static void init(){
        createCustomConfig();
        loadDefaults();
        save();

        heading = customConfig.getString("heading");
        rows = customConfig.getInt("num_rows");

        try {

            List<String> items = customConfig.getStringList("entries");

            for (String item : items){
                String[] data = item.split(";");

                String name = data[0];
                String target = data[1];
                String item_type = data[2];

                int x = Integer.parseInt(data[3]);
                int y = Integer.parseInt(data[4]);

                String lore = data[5];

                Item_Config item_config = new Item_Config(
                        x,y,
                        item_type,
                        name,
                        lore,
                        target
                );

                item_config.genItemStack();

                items_map.put(( y * 9 ) + x, item_config);
            }


        }catch (Exception e){
            System.out.println(EPS_API.PREFIX + "Malformed GUI-Item. Skipping.");
            e.printStackTrace();
        }

    }

    // GUI Item Schema: <name>;<target>;<item_type>;<x>;<y>;<lore>
    private static void loadDefaults(){
        customConfig.addDefault("heading", "Server Selection");
        customConfig.addDefault("num_rows", 1);
        customConfig.addDefault("entries", new String[]{
                "§6PvP;pvp;diamond_sword;3;0;§5Click To Join PvP (§4%s§5)",
                "§2Survival;survival;grass;4;0;§5Click To Join Survival (§4%s§5)",
                "§cMinigames;minigames;tnt;5;0;§5Click To Join Minigamess (§4%s§5)"
        });
        customConfig.options().copyDefaults(true);
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

    public static Inventory generateInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, rows * 9, "Menu: " + heading);

        for (Item_Config item : items_map.values()){
            inventory.setItem((item.y * 9 ) + item.x, item.genItemStack());
        }

        return inventory;
    }
}

