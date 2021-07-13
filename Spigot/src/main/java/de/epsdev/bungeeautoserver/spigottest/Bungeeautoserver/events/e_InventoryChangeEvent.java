package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class e_InventoryChangeEvent implements Listener {
    @EventHandler
    void onDrag(InventoryClickEvent e){
        System.out.println(1);
        InventoryView inventoryView = e.getView();

        if(inventoryView.getTitle().startsWith("Menu:")){
            e.setCancelled(true);
        }
    }
}
