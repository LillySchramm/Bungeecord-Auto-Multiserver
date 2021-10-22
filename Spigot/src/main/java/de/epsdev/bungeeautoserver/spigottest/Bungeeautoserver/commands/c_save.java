package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.interfaces.FTPStatusEmitter;
import de.epsdev.bungeeautoserver.api.tools.FTPManagement;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_save implements CommandExecutor {
    public static int saveDelay = 15;
    public static final FTPStatusEmitter FTP_STATUS_EMITTER =  new FTPStatusEmitter() {
        @Override
        public void startDownload(String directoryName) {}
        @Override
        public void startUpload(String directoryName) {}
        @Override
        public void finishDownload(long size) {}
        @Override
        public void finishUpload(long size) {}
        @Override
        public void finishTotal(long totalSize) {
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "...Done! Total: " + (totalSize / 1000000) + "MB");
        }
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Saving the world to remote server...");
            FTPManagement.ftpStatusEmitter = FTP_STATUS_EMITTER;

            for (World world : Bukkit.getWorlds()) world.save();
            Bukkit.savePlayers();

            Bukkit.getScheduler().scheduleSyncDelayedTask(BungeecordAutoConfig.plugin, () -> {
                try {
                    FTPManagement.uploadWorld(
                            EPS_API.ftpServerAddress,
                            EPS_API.ftpServerPort,
                            EPS_API.ftpServerUser,
                            EPS_API.ftpServerPassword
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 20L * saveDelay);
        }
        return true;
    }
}
