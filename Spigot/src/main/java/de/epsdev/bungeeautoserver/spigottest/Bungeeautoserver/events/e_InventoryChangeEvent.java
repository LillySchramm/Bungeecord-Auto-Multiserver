package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.events;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.packages.RequestChangePlayerServerPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.GUI.Item_Config;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.config.GUI_Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import java.util.ArrayList;

public class e_InventoryChangeEvent implements Listener {
    @EventHandler
    void onDrag(InventoryClickEvent e){
        try {
            int raw_slot = e.getRawSlot();
            int slot = e.getSlot();

            InventoryView inventoryView = e.getView();

            if(inventoryView.getTitle().startsWith("Menu:")){
                e.setCancelled(true);

                if (raw_slot == slot){
                    Player player = (Player) e.getInventory().getHolder();
                    player.closeInventory();

                    Item_Config item_config = GUI_Config.items_map.get(slot);

                    int[] totals = new int[0];

                    if (item_config != null){
                        totals = ServerManager.calcTotals(EPS_API.serverInfo.getOrDefault(item_config.target , new ArrayList<>()));
                    }

                    if(totals[0] == totals[1]){
                        player.sendMessage(ChatColor.RED + EPS_API.PREFIX + "The server you are trying to join is full.");

                        return;
                    }

                    BungeecordAutoConfig.eps_api.connection.send(new RequestChangePlayerServerPackage(
                            item_config.target,
                            player.getDisplayName(),
                            false
                    ));
                }
            }
        }catch (Exception ignored){}
    }
}
