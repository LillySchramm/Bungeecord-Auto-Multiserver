package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands;

import de.epsdev.bungeeautoserver.api.ServerInfo;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.packages.RequestServerStatusPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.GUI.Item_Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class c_menu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            Inventory inventory = Bukkit.createInventory(player,6*9, "Menu: Test");
            Item_Config item_config = new Item_Config(
                    2,
                    2,
                    "ice",
                    "HUB",
                    "§5 Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt §1 ut labore et dolore magna aliquyam erat, sed diam voluptua. "
            );

            inventory.setItem((item_config.y * 9) + item_config.x, item_config.genItemStack());


            player.openInventory(inventory);
        }

        return true;
    }
}
