package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.config.Config;
import de.epsdev.bungeeautoserver.api.enums.OperationType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BungeecordAutoConfig extends JavaPlugin {
    public FileConfiguration config = getConfig();
    public static EPS_API eps_api;

    @Override
    public void onEnable() {
        init_config();

        if(Config.isBungeeReady()){
            eps_api = new EPS_API(OperationType.CLIENT);
            eps_api.setRemoteAddress(config.getString("bungee_address"));
            eps_api.setPort(Bukkit.getPort());
            eps_api.setMax_players(Bukkit.getMaxPlayers());
            eps_api.setType(config.getString("server_type"));

            EPS_API.key = config.getString("bungee_password");
            System.out.println( "sssssssssss" + config.getString("bungee_password"));
            System.out.println( "sssssssssss" + EPS_API.key);

            eps_api.init();
        }else {
            Bukkit.shutdown();
        }
    }

    @Override
    public void onDisable() {
        eps_api.disable();
    }

    public void init_config(){
        config.addDefault("bungee_address", "0.0.0.0");
        config.addDefault("bungee_password", "");
        config.addDefault("server_type", "Hub");

        config.options().copyDefaults(true);

        saveConfig();
    }
}
