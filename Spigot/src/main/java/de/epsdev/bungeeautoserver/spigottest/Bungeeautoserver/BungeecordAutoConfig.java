package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.config.Config;
import de.epsdev.bungeeautoserver.api.enums.OperationType;
import de.epsdev.bungeeautoserver.api.interfaces.FTPStatusEmitter;
import de.epsdev.bungeeautoserver.api.packages.AnnounceBroadcastPackage;
import de.epsdev.bungeeautoserver.api.packages.AnnounceRestartPackage;
import de.epsdev.bungeeautoserver.api.tools.FTPManagement;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands.*;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.config.GUI_Config;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.events.e_InventoryChangeEvent;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.events.e_OnBlockInteract;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.events.e_OnSignChange;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class BungeecordAutoConfig extends JavaPlugin {
    public static FileConfiguration config;
    public static EPS_API eps_api;
    public static JavaPlugin plugin = null;

    @Override
    public void onEnable() {
        config = getConfig();
        plugin = this;

        FTPManagement.ftpStatusEmitter = new FTPStatusEmitter() {
            @Override
            public void startDownload(String directoryName) {
                System.out.println("Downloading " + directoryName);
            }

            @Override
            public void startUpload(String directoryName) {
                System.out.println("Uploading " + directoryName);
            }

            @Override
            public void finishDownload(long size) {
                System.out.println("Done: " + size / 1000 + "kb");
            }

            @Override
            public void finishUpload(long size) {
                System.out.println("Done: " + size / 1000 + "kb");
            }

            @Override
            public void finishTotal(long totalSize) {
                System.out.println("TOTAL: " + totalSize / 1000 + "kb");
            }
        };

        init_config();

        SignManager.loadAllSigns();
        SignManager.startSignUpdateScheduler();

        // Restart Management

        AnnounceRestartPackage.restartEmitter = () -> {
            System.out.println(EPS_API.PREFIX + "Proxy has restarted.");
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, Bukkit::shutdown, 20L * 5);
        };

        // Broadcast Management

        AnnounceBroadcastPackage.broadcastEmitter = message -> {
            System.out.println(message);
            getServer().broadcast( ChatColor.RED + "" + ChatColor.BOLD +
                    " [BROADCAST] " + message, "");
        };

        // Commands

        getCommand("changeserver").setExecutor(new c_ChangeServer());
        getCommand("changeinstance").setExecutor(new c_ChangeInstance());
        getCommand("getServerInfo").setExecutor(new c_getServerInfo());
        getCommand("closeserver").setExecutor(new c_closeserver());
        getCommand("openserver").setExecutor(new c_openserver());
        getCommand("menu").setExecutor(new c_menu());
        getCommand("broadcast").setExecutor(new c_broadcast());
        getCommand("ban").setExecutor(new c_ban());
        getCommand("unban").setExecutor(new c_unban());
        getCommand("uwu").setExecutor(new c_uwu());
        getCommand("save").setExecutor(new c_save());

        // Events

        getServer().getPluginManager().registerEvents(new e_OnSignChange(), this);
        getServer().getPluginManager().registerEvents(new e_OnBlockInteract(), this);
        getServer().getPluginManager().registerEvents(new e_InventoryChangeEvent(), this);

        // Updates

        if(Config.isBungeeReady()){
            eps_api = new EPS_API(OperationType.CLIENT);
            eps_api.setRemoteAddress(config.getString("bungee_address"));
            eps_api.setPort(Bukkit.getPort());
            eps_api.setMax_players(Bukkit.getMaxPlayers());
            eps_api.setType(config.getString("server_type"));

            EPS_API.key = config.getString("bungee_password");

            eps_api.init();

            GUI_Config.init();

            while (EPS_API.backupChannelName.equals("")) {}

            if (!EPS_API.ftpServerAddress.equals("")) {
                try {
                    FTPManagement.downloadWorld(
                            EPS_API.ftpServerAddress,
                            EPS_API.ftpServerPort,
                            EPS_API.ftpServerUser,
                            EPS_API.ftpServerPassword
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            Bukkit.shutdown();
        }
    }

    @Override
    public void onDisable() {
        if(eps_api != null) eps_api.disable();
    }

    public void init_config(){
        config.addDefault("bungee_address", "0.0.0.0");
        config.addDefault("bungee_password", "");
        config.addDefault("server_type", "Hub");
        config.addDefault("signs", new String[0]);

        config.options().copyDefaults(true);

        saveConfig();
    }
}
