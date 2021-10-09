package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.tools.FTPManagement;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class c_save implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Bukkit.getWorld("world").save();

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

        System.out.println(111111);
        return true;
    }
}
